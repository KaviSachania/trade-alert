package com.cryptoalert.cryptoalert.config;

import com.cryptoalert.cryptoalert.repositories.AlertsRepository;
import com.cryptoalert.cryptoalert.repositories.PricesRepository;
import com.cryptoalert.cryptoalert.repositories.StocksRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlertsConfig {

    @Bean
    CommandLineRunner commandLineRunner(AlertsRepository alertsRepository, PricesRepository pricesRepository, StocksRepository stocksRepository) {
        return args -> {
//            List<Stock> stocks = new ArrayList<Stock>();
//
//            stocks.add(new Stock(
//                    "btc",
//                    "Bitcoin",
//                    "BTC",
//                    3
//            ));
//            stocks.add(new Stock(
//                    "eth",
//                    "Ethereum",
//                    "ETH",
//                    3
//            ));
//            stocks.add(new Stock(
//                    "doge",
//                    "DogeCoin",
//                    "DOGE",
//                    3
//            ));
//            stocks.add(new Stock(
//                    "algo",
//                    "Algorand",
//                    "ALGO",
//                    3
//            ));
//
//            stocksRepository.saveAll(stocks);


//            Alert first = new Alert(
//                    "asdf",
//                    true,
//                    730,
//                    14
//            );
//            Alert second = new Alert(
//                    "1423",
//                    stocks.get(0).id,
//                    -0.0005,
//                    false,
//                    30,
//                    90,
//                    1
//            );

//            alertsRepository.saveAll(ImmutableSet.of(first));


//            Price firstP = new Price(
//                    "price1",
//                    stocks.get(0).id,
//                    300,
//                    900
//            );
//            Price secondP = new Price(
//                    "price2",
//                    stocks.get(1).id,
//                    40,
//                    6
//            );
//
//            pricesRepository.saveAll(ImmutableSet.of(firstP,secondP));
        };
    }

}
