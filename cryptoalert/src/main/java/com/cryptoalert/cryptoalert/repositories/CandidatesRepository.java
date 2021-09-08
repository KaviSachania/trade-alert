package com.cryptoalert.cryptoalert.repositories;

import com.cryptoalert.cryptoalert.mappers.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface CandidatesRepository extends JpaRepository<Candidate, String> {

    @Query(value = "SELECT T1.* FROM Candidate as T1 ORDER BY T1.length DESC", nativeQuery = true)
    public List<Candidate> getAllCandidates();

    @Query(value = "SELECT T1.* FROM Candidate as T1 WHERE T1.is_new = TRUE ORDER BY T1.length DESC", nativeQuery = true)
    public List<Candidate> getNewCandidates();

    @Query(value = "SELECT T1.* FROM Candidate as T1 WHERE T1.id = ?1", nativeQuery = true)
    Candidate getCandidateById(String candidateId);

    @Query(value = "SELECT T1.* FROM Candidate as T1 WHERE T1.stock_id = ?1 ORDER BY t1.rebounds DESC", nativeQuery = true)
    public List<Candidate> getCandidatesByStockId(String stockId);

    @Query(value = "SELECT T1.* FROM Candidate as T1 WHERE T1.alert_id = ?1 ORDER BY t1.rebounds DESC", nativeQuery = true)
    public List<Candidate> getCandidatesByAlertId(String alertId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Candidate (id, stock_id, trend, rebounds, slope, anchor_price, anchor_time, length, is_new, created_at) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)",
                                      nativeQuery = true)
    public void insertCandidate(String id,
                                String stockId,
                                int trend,
                                int rebounds,
                                double slope,
                                double anchorPrice,
                                int anchorTime,
                                long length,
                                boolean isNew,
                                LocalDateTime createdAt);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Candidate SET stock_id = ?2, trend = ?3, rebounds = ?4, slope = ?5, anchor_price = ?6, anchor_time = ?7, length = ?8, is_new = ?9, created_at = ?10 WHERE id = ?1",
            nativeQuery = true)
    public int updateCandidate(String id,
                               String stockId,
                               int trend,
                               int rebounds,
                               double slope,
                               double anchorPrice,
                               int anchorTime,
                               long length,
                               boolean isNew,
                               LocalDateTime createdAt);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Candidate WHERE id = ?1", nativeQuery = true)
    public int deleteCandidate(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Candidate WHERE id IN ?1", nativeQuery = true)
    public int deleteCandidatesByIds(Collection<String> candidateIds);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Candidate WHERE stock_id = ?1", nativeQuery = true)
    public int deleteCandidatesByStockId(String stockid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Candidate WHERE stock_id IN ?1", nativeQuery = true)
    public int deleteCandidatesByStockIds(Collection<String> stockids);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Candidate", nativeQuery = true)
    public int deleteAllCandidates();

}
