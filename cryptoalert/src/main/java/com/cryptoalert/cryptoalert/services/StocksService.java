package com.cryptoalert.cryptoalert.services;

import com.cryptoalert.cryptoalert.mappers.Stock;
import com.cryptoalert.cryptoalert.repositories.CandidatesRepository;
import com.cryptoalert.cryptoalert.repositories.PricesRepository;
import com.cryptoalert.cryptoalert.repositories.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
public class StocksService {

    private final CandidatesRepository candidatesRepository;
    private final PricesRepository pricesRepository;
    private final StocksRepository stocksRepository;

    @Autowired
    public StocksService(CandidatesRepository candidatesRepository,
                         PricesRepository pricesRepository,
                         StocksRepository stocksRepository) {
        this.candidatesRepository = candidatesRepository;
        this.pricesRepository = pricesRepository;
        this.stocksRepository = stocksRepository;
    }

    public List<Stock> getAllStocks() {
        return stocksRepository.getAllStocks();
    }

    public Stock getStockById(String stockId) {
        return stocksRepository.getStockById(stockId);
    }

    public Stock getStockByTicker(String ticker) {
        return stocksRepository.getStockByTicker(ticker);
    }

    public List<Stock> getStocksByTickers(Collection<String> tickers) {
        return stocksRepository.getStocksByTickers(tickers);
    }

    public List<Stock> getStocksByIds(Collection<String> stockIds) {
        return stocksRepository.getStocksByIds(stockIds);
    }

    public void insertStock(Stock stock) {
        stocksRepository.insertStock(
                stock.id,
                stock.name,
                stock.ticker,
                stock.marketCap
        );
    }

    public void insertStocks(Collection<Stock> stocks) {
        for (Stock stock : stocks) {
            stocksRepository.insertStock(
                    stock.id,
                    stock.name,
                    stock.ticker,
                    stock.marketCap
            );
        }
    }

    public int updateStock(Stock stock) {
        return stocksRepository.updateStock(stock.id, stock.name, stock.ticker, stock.marketCap);
    }

    public int deleteStock(Stock stock) {
        candidatesRepository.deleteCandidatesByStockId(stock.id);
        pricesRepository.deletePricesByStockId(stock.id);

        return stocksRepository.deleteStock(stock.id);
    }

    public int deleteStocks(Collection<Stock> stocks) {
        Set<String> stockIds = stocks.stream().map(stock -> stock.id).collect(Collectors.toSet());

        candidatesRepository.deleteCandidatesByStockIds(stockIds);
        pricesRepository.deletePricesByStockIds(stockIds);

        return stocksRepository.deleteStocksByIds(stockIds);
    }
}
