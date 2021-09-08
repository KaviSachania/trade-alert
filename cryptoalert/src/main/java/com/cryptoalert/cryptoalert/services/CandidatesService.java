package com.cryptoalert.cryptoalert.services;

import com.cryptoalert.cryptoalert.mappers.Candidate;
import com.cryptoalert.cryptoalert.repositories.CandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Component
public class CandidatesService {

    private final CandidatesRepository candidatesRepository;

    @Autowired
    public CandidatesService(CandidatesRepository candidatesRepository) {
        this.candidatesRepository = candidatesRepository;
    }

    public List<Candidate> getAllCandidates() {
        return candidatesRepository.getAllCandidates();
    }

    public List<Candidate> getNewCandidates() {
        return candidatesRepository.getNewCandidates();
    }

    public Candidate getCandidateById(String candidateId) {
        return candidatesRepository.getCandidateById(candidateId);
    }

    public List<Candidate> getCandidatesByStockId(String stockId) {
        return candidatesRepository.getCandidatesByStockId(stockId);
    }

    public List<Candidate> getCandidatesByAlertId(String alertId) {
        return candidatesRepository.getCandidatesByAlertId(alertId);
    }

    public void insertCandidate(Candidate candidate) {
        candidatesRepository.insertCandidate(
                candidate.id,
                candidate.stockId,
                candidate.trend,
                candidate.rebounds,
                candidate.slope,
                candidate.anchorPrice,
                candidate.anchorTime,
                candidate.length,
                candidate.isNew,
                candidate.createdAt
        );
    }

    public void insertCandidates(Collection<Candidate> candidates) {
        candidatesRepository.saveAll(candidates);
    }

    public int updateCandidate(Candidate candidate) {
        return candidatesRepository.updateCandidate(
                candidate.id,
                candidate.stockId,
                candidate.trend,
                candidate.rebounds,
                candidate.slope,
                candidate.anchorPrice,
                candidate.anchorTime,
                candidate.length,
                candidate.isNew,
                candidate.createdAt
        );
    }

    public int deleteCandidate(Candidate candidate) {
        return candidatesRepository.deleteCandidate(candidate.id);
    }

    public int deleteCandidatesByIds(Collection<String> candidateIds) {
        return candidatesRepository.deleteCandidatesByIds(candidateIds);
    }

    public int deleteCandidatesByStockId(String stockId) {
        return candidatesRepository.deleteCandidatesByStockId(stockId);
    }

    public int deleteCandidatesByStockIds(Collection<String> stockIds) {
        return candidatesRepository.deleteCandidatesByStockIds(stockIds);
    }

    public int deleteAllCandidates() {
        return candidatesRepository.deleteAllCandidates();
    }

}
