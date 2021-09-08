package com.cryptoalert.cryptoalert.controllers;

import com.cryptoalert.cryptoalert.conf.SymbolConfiguration;
import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.mappers.Stock;
import com.cryptoalert.cryptoalert.services.PricesService;
import com.cryptoalert.cryptoalert.services.StocksService;
import com.cryptoalert.cryptoalert.utils.MessagesUtil;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MessagingController {

    private final PricesService pricesService;
    private final StocksService stocksService;

    private static final ArrayList<String> SYMBOL_LIST = SymbolConfiguration.getSymbolList();

    @Autowired
    public MessagingController(PricesService pricesService, StocksService stocksService) {
        this.pricesService = pricesService;
        this.stocksService = stocksService;
    }

    @PostMapping(path="/messages", produces = "text/plain")
    public String service(@RequestParam("From") String fromNumber,
                          @RequestParam("Body") String messageBody) {
        if ((messageBody.toUpperCase().equals("H")) || (messageBody.toUpperCase().equals("HELP"))) {
            return getHelpMessage();
        } else if (messageBody.toUpperCase().equals("ALL")) {
            return getAllPrices();
        } else {
            return getPriceByTicker(messageBody);
        }
    }

    private String getAllPrices() {
        List<Stock> stocks = stocksService.getAllStocks();
        if (stocks.size() == 0) {
            return String.format("Could not find stocks.");
        }

        Map<String, Stock> stockMap = stocks.stream()
                .collect(Collectors.toMap(stock -> stock.id, stock -> stock));
        Set<String> stockIds = stockMap.keySet();

        List<Price> prices = pricesService.getCurrentPricesByStockIds(stockIds);
        if (prices.size() == 0) {
            return String.format("Could not find prices.");
        }

        Map<String, Price> priceMap = prices.stream()
                .collect(Collectors.toMap(price -> price.stockId, price -> price));

        String pricesMessage = "";
        for (int i = 0; i < prices.size(); i++) {
//            pricesMessage = pricesMessage.concat(MessagesUtil.priceMessageBuilder(prices.get(i), stocks.get(i).ticker));
            pricesMessage = stockIds.stream()
                    .reduce("", (message, stockId)
                    ->  message + MessagesUtil.priceMessageBuilder(priceMap.get(stockId), stockMap.get(stockId).ticker));
        }

        return pricesMessage.trim();
    }

    private String getPriceByTicker(String ticker) {
        Stock stock = stocksService.getStockByTicker(ticker);
        if (stock == null) {
            return String.format("Ticker %s not found.", ticker.toUpperCase());
        }

        Price price = pricesService.getCurrentPriceByStockId(stock.id);
        if (price == null) {
            return String.format("Price for %s not found.", ticker.toUpperCase());
        }

        return MessagesUtil.priceMessageBuilder(price, ticker).trim();
    }

    private String getHelpMessage() {
        List<Stock> stocks = stocksService.getAllStocks();
        if (stocks.size() == 0) {
            return String.format("Could not find stocks.");
        }

        // change to order stockTickers alphabetically
        Set<String> stockTickers = stocks.stream().map((stock) -> stock.ticker).collect(ImmutableSet.toImmutableSet());

        return stockTickers.stream().reduce("Available ticker symbols:\n",
                (helpMessage, ticker) -> helpMessage + ticker + "\n").trim();
    }

}
