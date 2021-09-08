package com.cryptoalert.cryptoalert.controllers;

import com.cryptoalert.cryptoalert.controllers.responseEntities.ResponseMessageListWithRelatablesEntity;
import com.cryptoalert.cryptoalert.controllers.responseEntities.ResponseMessageWithRelatablesEntity;
import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.mappers.Stock;
import com.cryptoalert.cryptoalert.services.PricesService;
import com.cryptoalert.cryptoalert.services.StocksService;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class StocksController {

    private final PricesService pricesService;
    private final StocksService stocksService;

    @Autowired
    public StocksController(PricesService pricesService, StocksService stocksService) {
        this.pricesService = pricesService;
        this.stocksService = stocksService;
    }

    @RequestMapping(
            path="/stocks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageListWithRelatablesEntity<Stock>> getAllStocks() {
        List<Stock> stocks = stocksService.getAllStocks();

        if (stocks.isEmpty()) {
            System.out.println("Could not find stocks.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Set<String> stockIds = stocks.stream()
                .map((stock) -> stock.id)
                .collect(ImmutableSet.toImmutableSet());
        List<Price> prices = pricesService.getCurrentPricesByStockIds(stockIds);

        ResponseMessageListWithRelatablesEntity<Stock> message =
                new ResponseMessageListWithRelatablesEntity<Stock>(stocks);
        message.addAllRelatables(prices);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(
            path="/stocks/{stockId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageWithRelatablesEntity<Stock>> getStockById(
            @PathVariable("stockId") final String stockId) {
        Stock stock = stocksService.getStockById(stockId);
        if (stock == null) {
            System.out.println("Could not find stock.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Price price = pricesService.getCurrentPriceByStockId(stock.id);

        ResponseMessageWithRelatablesEntity<Stock> message = new ResponseMessageWithRelatablesEntity<Stock>(stock);
        message.addRelatable(price);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
