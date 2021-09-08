package com.cryptoalert.cryptoalert.controllers;

import com.cryptoalert.cryptoalert.controllers.responseEntities.ResponseMessageListWithRelatablesEntity;
import com.cryptoalert.cryptoalert.controllers.responseEntities.ResponseMessageWithRelatablesEntity;
import com.cryptoalert.cryptoalert.mappers.Candidate;
import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.mappers.Stock;
import com.cryptoalert.cryptoalert.services.CandidatesService;
import com.cryptoalert.cryptoalert.services.PricesService;
import com.cryptoalert.cryptoalert.services.StocksService;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class CandidatesController {

    private final CandidatesService candidatesService;
    private final PricesService pricesService;
    private final StocksService stocksService;

    @Autowired
    public CandidatesController(CandidatesService candidatesService, PricesService pricesService, StocksService stocksService) {
        this.candidatesService = candidatesService;
        this.pricesService = pricesService;
        this.stocksService = stocksService;
    }

    @RequestMapping(
            path="/candidates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageListWithRelatablesEntity<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidatesService.getAllCandidates();

        Set<String> stockIds = candidates.stream()
                .map((candidate) -> candidate.stockId)
                .collect(ImmutableSet.toImmutableSet());
        List<Stock> stocks = stocksService.getStocksByIds(stockIds);
        //List<Price> prices = pricesService.getCurrentPricesByStockIds(stockIds);

        ResponseMessageListWithRelatablesEntity<Candidate> message =
                new ResponseMessageListWithRelatablesEntity<Candidate>(candidates);
        message.addAllRelatables(stocks);
        //message.addAllRelatables(prices);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(
            path="/candidates/{candidateId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageWithRelatablesEntity<Candidate>> getCandidateById(
            @PathVariable("candidateId") final String candidateId) {
        Candidate candidate = candidatesService.getCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("Could not find stock.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Price price = pricesService.getCurrentPriceByStockId(candidate.id);

        ResponseMessageWithRelatablesEntity<Candidate> message = new ResponseMessageWithRelatablesEntity<Candidate>(candidate);
        message.addRelatable(price);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
