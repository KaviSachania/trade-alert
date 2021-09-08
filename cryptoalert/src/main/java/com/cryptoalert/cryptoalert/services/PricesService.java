package com.cryptoalert.cryptoalert.services;

import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Component
public class PricesService {

    private final PricesRepository pricesRepository;

    @Autowired
    public PricesService(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    public List<Price> getAllPrices() {
        return pricesRepository.getAllPrices();
    }

    public List<Price> getPricesByStockId(String stockId) {
        return pricesRepository.getPricesByStockId(stockId);
    }

    public Price getPriceById(String priceId) {
        return pricesRepository.getPriceById(priceId);
    }

    public Price getCurrentPriceByStockId(String stockId) {
        return pricesRepository.getCurrentPriceByStockId(stockId, (System.currentTimeMillis()/1000)-90);
    }

    public List<Price> getCurrentPricesByStockIds(Collection<String> stockIds) {
//        List<Price> prices = stockIds.stream()
//                .map(stockId -> pricesRepository.getCurrentPriceByStockId(stockId))
//                .collect(Collectors.toList());

        List<Price> prices = pricesRepository.getCurrentPricesByStockIds(stockIds);

        return prices;
    }

    public Price getPriceByStockIdAndTime(String stockId, long time) {
        return pricesRepository.getPriceByStockIdAndTime(stockId, time, time-90);
    }

    public void insertPrice(Price price) {
        pricesRepository.insertPrice(price.id, price.stockId, price.open, price.close, price.high, price.low, price.time);
    }

    public void insertPrices(Collection<Price> prices) {
        //pricesRepository.insertPrices(prices);
        pricesRepository.saveAll(prices);
//        for (Price price : prices) {
//            pricesRepository.insertPrice(price.id, price.stockId, price.open, price.close, price.high, price.low, price.time);
//        }
    }

    public int updatePrice(Price price) {
        return pricesRepository.updatePrice(price.id, price.stockId, price.open, price.close, price.high, price.low, price.time);
    }

    public int deletePrice(Price price) {
        return pricesRepository.deletePrice(price.id);
    }

    public int deleteAllPrices() {
        return pricesRepository.deleteAllPrices();
    }

    public int deletePricesByStockId(String stockId) {
        return pricesRepository.deletePricesByStockId(stockId);
    }

    public int deletePricesByStockIds(Collection<String> stockIds) {
        return pricesRepository.deletePricesByStockIds(stockIds);
    }
}
