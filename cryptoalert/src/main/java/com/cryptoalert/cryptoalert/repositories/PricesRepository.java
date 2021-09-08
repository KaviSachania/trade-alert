package com.cryptoalert.cryptoalert.repositories;

import com.cryptoalert.cryptoalert.mappers.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface PricesRepository extends JpaRepository<Price, String> {

    @Query(value = "SELECT T1.* FROM Price as T1 ORDER BY T1.time DESC", nativeQuery = true)
    public List<Price> getAllPrices();

    @Query(value = "SELECT T1.* FROM Price as T1 WHERE T1.id = ?1", nativeQuery = true)
    Price getPriceById(String priceId);

    @Query(value = "SELECT T1.* FROM Price as T1 WHERE T1.stock_id = ?1 ORDER BY t1.time ASC", nativeQuery = true)
    public List<Price> getPricesByStockId(String stockId);

    @Query(value = "SELECT T1.* FROM Price as T1 WHERE T1.stock_id = ?1 AND T1.time >= ?2 ORDER BY t1.time DESC LIMIT 1",
            nativeQuery = true)
    public Price getCurrentPriceByStockId(String stockId, long offsetTime);

    @Query(value = "SELECT T1.* \n" +
            "          FROM Price as T1 \n" +
            "          WHERE \n" +
            "            T1.stock_id IN ?1 AND\n" +
            "            T1.id = (\n" +
            "              SELECT T2.id\n" +
            "                FROM Price as T2\n" +
            "                WHERE\n" +
            "                  T2.stock_id = T1.stock_id\n" +
            "                ORDER BY T2.time DESC LIMIT 1\n" +
            "            )",
            nativeQuery = true)
    public List<Price> getCurrentPricesByStockIds(Collection<String> stockIds);

//    """
//        SELECT T1.*
//          FROM Price as T1
//          WHERE
//            T1.stock_id IN ?1 AND
//            T1.id = (
//              SELECT TOP 1 T2.id
//                FROM Price as T2
//                WHERE
//                  T2.stock_id = T1.stock_id AND
//                ORDER BY T2.time DESC
//            )
//    """

    @Query(value = "SELECT T1.* FROM Price as T1 WHERE T1.stock_id = ?1 AND T1.time <= ?2 AND T1.time >= ?3 ORDER BY t1.time DESC LIMIT 1",
            nativeQuery = true)
    public Price getPriceByStockIdAndTime(String stockId, long time, long offsetTime);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Price (id, stock_id, open, close, high, low, time) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)",
                                      nativeQuery = true)
    public void insertPrice(String id,
                            String stockId,
                            double open,
                            double close,
                            double high,
                            double low,
                            long time);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Price (id, stock_id, open, close, high, low, time) VALUES (?1)",
            nativeQuery = true)
    public void insertPrices(Collection<Price> prices);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Price SET stock_id = ?2, open = ?3, close = ?4, high = ?5, low = ?6, time = ?7 WHERE id = ?1",
            nativeQuery = true)
    public int updatePrice(String id,
                           String stockId,
                           double open,
                           double close,
                           double high,
                           double low,
                           long time);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Price WHERE id = ?1", nativeQuery = true)
    public int deletePrice(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Price", nativeQuery = true)
    public int deleteAllPrices();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Price WHERE stock_id = ?1", nativeQuery = true)
    public int deletePricesByStockId(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Price WHERE stock_id IN ?1", nativeQuery = true)
    public int deletePricesByStockIds(Collection<String> stockIds);
}
