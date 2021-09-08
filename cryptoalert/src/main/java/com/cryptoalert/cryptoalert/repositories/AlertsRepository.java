package com.cryptoalert.cryptoalert.repositories;

import com.cryptoalert.cryptoalert.mappers.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AlertsRepository extends JpaRepository<Alert, String> {

    @Query(value = "SELECT T1.* FROM Alert as T1", nativeQuery = true)
    public List<Alert> getAllAlerts();

    @Query(value = "SELECT T1.* FROM Alert as T1 WHERE T1.user_id = ?1", nativeQuery = true)
    public List<Alert> getAlertsByUserId(Long userId);

    @Query(value = "SELECT T1.* FROM Alert as T1 WHERE T1.id = ?1", nativeQuery = true)
    Alert getAlertById(String id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Alert (id, trend, start_time, end_time) " +
            "VALUES (?1, ?2, ?3, ?4)",
            nativeQuery = true)
    public void insertAlert(String id,
                            int trend,
                            int startTime,
                            int endTime);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Alert SET trend = ?2, min_slope=?3, min_length=?4, min_market_cap=?5 WHERE id = ?1", nativeQuery = true)
    public int updateAlert(String id, int trend, double minSlope, int minLength, int minMarketCap);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Alert WHERE id = ?1", nativeQuery = true)
    public int deleteAlert(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Alert WHERE user_id = ?1", nativeQuery = true)
    public int deleteAlertsByUserId(Long userId);

}
