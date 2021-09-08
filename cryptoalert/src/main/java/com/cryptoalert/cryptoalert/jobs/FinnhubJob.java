package com.cryptoalert.cryptoalert.jobs;

import com.cryptoalert.cryptoalert.mappers.FinnhubPrice;
import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.mappers.Stock;
import com.cryptoalert.cryptoalert.services.PricesService;
import com.cryptoalert.cryptoalert.services.ServiceFactory;
import com.cryptoalert.cryptoalert.services.StocksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class FinnhubJob {

//    @Value("${FINNHUB_API_KEY}")
//    private static final String FINNHUB_TOKEN;


//    @Autowired
//    private Environment env;
//    private static final String FINNHUB_TOKEN = env.getProperty("FINNHUB_API_KEY");

    private static final String FINNHUB_TOKEN = System.getenv("FINNHUB_API_KEY");

//    private static final String FINNHUB_TOKEN = Config.getFinnhubToken();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String BASE_URL = "https://finnhub.io/api/v1/";

    private static final int NUM_DAYS_LOOKBACK = 730;

    private static final Set<String> EXCHANGES = ImmutableSet.of("NASDAQ NMS - GLOBAL MARKET", "NEW YORK STOCK EXCHANGE, INC.");

    public static void updateAllStocks() {
        StocksService stocksService = ServiceFactory.getStocksService();

        try {
            String tokenUrlQuery = URLEncoder.encode(FINNHUB_TOKEN, StandardCharsets.UTF_8);

            URL url = new URL(BASE_URL + "stock/symbol?exchange=US&token=" + tokenUrlQuery);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
            String line;
            String response = "";
            while((line = reader.readLine()) != null) {
                response = line;
            }
            JSONArray stockArray = new JSONArray(response);

            List<Stock> stocks = new ArrayList<Stock>();
            for (int i = 0; i < 2000; i++) { // stockArray.length()
                JSONObject jsonStock = stockArray.getJSONObject(i);
                if (jsonStock.getString("type").equals("Common Stock")) {
                    stocks.add(new Stock(
                            UUID.randomUUID().toString(),
                            jsonStock.getString("description"),
                            jsonStock.getString("symbol"),
                            0
                    ));
                }
            }

            HashMap<String, Stock> stocksByTicker = new HashMap<String, Stock>();
            stocks.forEach(stock -> stocksByTicker.put(stock.ticker, stock));

            //Set<String> newStockTickers = stocks.stream().map(stock -> stock.ticker).collect(Collectors.toSet());
            Set<String> newStockTickers = stocksByTicker.keySet();

            List<Stock> oldStocks = stocksService.getAllStocks();
            Set<String> oldStockTickers = oldStocks.stream().map(stock -> stock.ticker).collect(Collectors.toSet());

            Set<String> removeSet = new HashSet<String>(oldStockTickers);
            removeSet.removeAll(newStockTickers);

            Set<String> addSet = new HashSet<String>(newStockTickers);
            addSet.removeAll(oldStockTickers);

            Set<String> allExchanges = new HashSet<>();

            HashSet<Stock> addStocks = new HashSet<Stock>();
            for (String ticker : addSet) {
                url = new URL(BASE_URL + "stock/profile2?symbol=" + ticker + "&token=" + tokenUrlQuery);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept", "application/json");
                responseStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(responseStream));
                response = "";
                while((line = reader.readLine()) != null) {
                    response = line;
                }
                System.out.println(response);
                if (response.equals("{}")) {
                    System.out.println(ticker);
                    continue;
                }
                JSONObject stockInfo = new JSONObject(response);

                allExchanges.add(stockInfo.getString("exchange"));

                if (EXCHANGES.contains(stockInfo.getString("exchange"))) {
                    int marketCap = stockInfo.getInt("marketCapitalization");

                    Stock stock = stocksByTicker.get(ticker);
                    stock.setMarketCap(marketCap);
                    addStocks.add(stock);
                }

                Thread.sleep(1500);
            }

            stocksService.insertStocks(addStocks);

            List<Stock> removeStocks = stocksService.getStocksByTickers(removeSet);
            stocksService.deleteStocks(removeStocks);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void updateAllPrices() {
        PricesService pricesService = ServiceFactory.getPricesService();
        StocksService stocksService = ServiceFactory.getStocksService();

        List<Stock> stocks = stocksService.getAllStocks();
        if (stocks.size() == 0) {
            System.out.println("Could not find stocks.");
        }

        Set<Price> prices = new HashSet<Price>();

        long startTime = System.currentTimeMillis() /1000;

        try {
            for (Stock stock : stocks) {
                System.out.println(stock.ticker);
                FinnhubPrice finnhubPrice = getPriceFromService(stock.ticker);
                if ((finnhubPrice == null) ||
                        (finnhubPrice.open == null) ||
                        (finnhubPrice.current == null) ||
                        (finnhubPrice.high == null) ||
                        (finnhubPrice.low == null)) {
                    System.out.println(String.format("Price for %s not found.", stock.ticker.toUpperCase()));
                    continue;
                }

                for (int i = 0; i<finnhubPrice.open.length; i++) {
                    Price price = new Price(UUID.randomUUID().toString(),
                            stock.id,
                            Double.parseDouble(finnhubPrice.open[i]),
                            Double.parseDouble(finnhubPrice.current[i]),
                            Double.parseDouble(finnhubPrice.high[i]),
                            Double.parseDouble(finnhubPrice.low[i]),
                            (System.currentTimeMillis() / 1000)-(86400*(NUM_DAYS_LOOKBACK-i)) // at days
                    );

                    prices.add(price);
                }

                Thread.sleep(1500);
            }

            pricesService.deleteAllPrices();
            pricesService.insertPrices(new ArrayList<Price>(prices));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis() /1000;
        System.out.println(endTime-startTime);
    }

    private static FinnhubPrice getPriceFromService(String ticker) {
        try {
//            String stockUrlQuery = URLEncoder.encode(ticker.toUpperCase(), StandardCharsets.UTF_8);
            String tokenUrlQuery = URLEncoder.encode(FINNHUB_TOKEN, StandardCharsets.UTF_8);

            URL url = new URL(BASE_URL + "stock/candle?symbol=" + ticker +
                    "&resolution=D" +
                    "&from=" + ((System.currentTimeMillis() / 1000) - (86400*NUM_DAYS_LOOKBACK)) + // at days
                    "&to=" + (System.currentTimeMillis() / 1000) +
                    "&token=" + tokenUrlQuery);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            FinnhubPrice finnhubPrice = MAPPER.readValue(responseStream, FinnhubPrice.class);

            return finnhubPrice;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static Optional<double[]> getWeekHistoryFromService(String symbol) {
        try {
            String stockUrlQuery = URLEncoder.encode(symbol.toUpperCase(), StandardCharsets.UTF_8);
            String tokenUrlQuery = URLEncoder.encode(FINNHUB_TOKEN, StandardCharsets.UTF_8);

            URL url = new URL(BASE_URL + "symbol=BINANCE:" + stockUrlQuery + "USDT" +
                    "&resolution=60" +
                    "&from=" + ((System.currentTimeMillis() - 90000000) / 1000) + // 25 hours ago
                    "&to=" + (System.currentTimeMillis() / 1000) +
                    "&token=" + tokenUrlQuery); // last 24 hours

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();

            String text = null;
            try (Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8.name())) {
                text = scanner.useDelimiter("\\A").next();
            }

            JSONArray jsonArray = new JSONObject(text).getJSONArray("c");

            double[] priceHistory = new double[24];
            for (int i = 0; i < 24; i++) {
                priceHistory[i] = jsonArray.getDouble(i);
            }

            return Optional.of(priceHistory);
        } catch (IOException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

}