package com.cryptoalert.cryptoalert.repositories;

import com.cryptoalert.cryptoalert.mappers.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface StocksRepository extends JpaRepository<Stock, String> {

    @Query(value = "SELECT T1.* FROM Stock as T1 ORDER BY T1.ticker", nativeQuery = true)
    public List<Stock> getAllStocks();

    @Query(value = "SELECT T1.* FROM Stock as T1 WHERE T1.id = ?1", nativeQuery = true)
    Stock getStockById(String stockId);

    @Query(value = "SELECT T1.* FROM Stock as T1 WHERE T1.ticker = ?1", nativeQuery = true)
    public Stock getStockByTicker(String ticker);

    @Query(value = "SELECT T1.* FROM Stock as T1 WHERE T1.ticker IN ?1 ORDER BY T1.ticker", nativeQuery = true)
    public List<Stock> getStocksByTickers(Collection<String> tickers);

    @Query(value = "SELECT T1.* FROM Stock as T1 WHERE T1.id IN ?1 ORDER BY T1.ticker", nativeQuery = true)
    public List<Stock> getStocksByIds(Collection<String> stockIds);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Stock (id, name, ticker, market_cap) VALUES (?1, ?2, ?3, ?4)",
                                      nativeQuery = true)
    public void insertStock(String id,
                            String name,
                            String ticker,
                            int marketCap);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Stock SET name = ?2, ticker = ?3, market_cap = ?4 WHERE id = ?1", nativeQuery = true)
    public int updateStock(String id, String name, String ticker, int marketCap);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Stock WHERE id = ?1", nativeQuery = true)
    public int deleteStock(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Stock WHERE id IN ?1", nativeQuery = true)
    public int deleteStocksByIds(Collection<String> stockIds);

}
