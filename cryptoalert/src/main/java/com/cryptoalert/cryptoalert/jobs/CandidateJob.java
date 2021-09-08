package com.cryptoalert.cryptoalert.jobs;

import com.cryptoalert.cryptoalert.mappers.Candidate;
import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.mappers.Stock;
import com.cryptoalert.cryptoalert.services.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CandidateJob implements Job {

    private static CandidateJob candidate_job_instance = null;

    private static final double PROXIMITY_THRESHOLD = 0.02;

    private static final int LOOKBACK_START = 730;
    private static final int LOOKBACK_END = 30;

    private static final int MIN_LAST_ANCHOR_DIST = 15;

    public CandidateJob() {
    }

    @Transactional
    public void findCandidates() {
        AlertsService alertsService = ServiceFactory.getAlertsService();
        CandidatesService candidatesService = ServiceFactory.getCandidatesService();
        PricesService pricesService = ServiceFactory.getPricesService();
        StocksService stocksService = ServiceFactory.getStocksService();

        if ((alertsService == null) || (candidatesService == null) || (pricesService == null) || (stocksService == null)) {
            return;
        }

        FinnhubJob.updateAllStocks();
        FinnhubJob.updateAllPrices();

        //candidatesService.deleteAllCandidates(); // change this when identifying new additions to candidate list

        long startTime = System.currentTimeMillis() /1000;

        Set<Candidate> candidateAddSet = new HashSet<Candidate>();

        List<Stock> stocks = stocksService.getAllStocks();
        for (Stock stock: stocks) {
            List<Price> stockPrices = pricesService.getPricesByStockId(stock.id);

            for (int searchI = 0; searchI<1; searchI++) {
                int maxRebounds = 0;
                double maxSlope = 0;
                double maxMinPrice = 0;
                int maxMinI = 0;
                int maxLength = 0;

                for (int startI = LOOKBACK_START; startI> LOOKBACK_END; startI--) {
                    List<Price> prices = stockPrices.subList(Math.max(stockPrices.size() - startI, 0),
                            stockPrices.size());

                    double slope = 0;
                    int rebounds = 0;
                    int firstReboundI = 0;

                    double minPrice = 1000000;
                    int minI = 0;
                    boolean atLine = false;

                    for (int i = 0; i < prices.size()-MIN_LAST_ANCHOR_DIST; i++) {
                        if (prices.get(i).low < minPrice) {
                            minPrice = prices.get(i).low;
                            minI = i;
                        }
                    }

                    if (minI < prices.size() - 1) {
                        slope = prices.get(minI + 1).low - minPrice;

                        for (int i = minI + 1; i < prices.size()-MIN_LAST_ANCHOR_DIST; i++) {
                            if (prices.get(i).low < getPoint(slope, minPrice, i - minI)) {
                                slope = (prices.get(i).low - minPrice) / (i - minI);
                            }
                        }

//                        slope = (prices.get(prices.size()-1).low - minPrice) / (prices.size()-1 - minI);

                        atLine = currentAtLine(slope, minPrice, prices.size() - 1 - minI, prices);
                        if (atLine) {
                            int[] reboundsResponse = numRebounds(slope, minPrice, minI, prices);
                            rebounds = reboundsResponse[0];
                            firstReboundI = reboundsResponse[1];
                        }

                        if (rebounds > maxRebounds) {
                            maxRebounds = rebounds;
                            maxSlope = slope;
                            maxMinPrice = minPrice;
                            maxMinI = prices.size()-minI-1;
                            maxLength = prices.size()-Math.min(firstReboundI, minI)-1;
                        }
                    }

                    if (minI > 0) {
                        slope = minPrice - prices.get(minI - 1).low;

                        for (int i = minI - 1; i >= 0; i--) {
                            if (prices.get(i).low < getPoint(slope, minPrice, i - minI)) {
                                slope = (prices.get(i).low - minPrice) / (i - minI);
                            }
                        }

//                        slope = (prices.get(prices.size()-1).low - minPrice) / (prices.size()-1 - minI);

                        rebounds = 0;

                        atLine = currentAtLine(slope, minPrice, prices.size() - 1 - minI, prices);
                        if (atLine) {
                            int[] reboundsResponse = numRebounds(slope, minPrice, minI, prices);
                            rebounds = reboundsResponse[0];
                            firstReboundI = reboundsResponse[1];
                        }

                        if (rebounds > maxRebounds) {
                            maxRebounds = rebounds;
                            maxSlope = slope;
                            maxMinPrice = minPrice;
                            maxMinI = prices.size()-minI-1;
                            maxLength = prices.size()-Math.min(firstReboundI, minI)-1;
                        }
                    }
                }

                if (maxRebounds > 1) {
                    boolean isNew = true;
                    LocalDateTime addTime = LocalDateTime.now().minusHours(6);

                    List<Candidate> existingCandidates = candidatesService.getCandidatesByStockId(stock.id);
                    if (existingCandidates.size() > 0) {
                        if (LocalDateTime.now().isAfter(existingCandidates.get(0).createdAt.plusDays(7))) {
                            isNew = false;
                        }

                        addTime = existingCandidates.get(0).createdAt;

                        for (Candidate existingCandidate : existingCandidates) {
                            candidatesService.deleteCandidate(existingCandidate);
                        }
                    }

                    Candidate candidate = new Candidate(
                            UUID.randomUUID().toString(),
                            stock.id,
                            0,
                            maxRebounds,
                            (500*maxSlope/stockPrices.get(stockPrices.size() - 1).close),
                            maxMinPrice,
                            maxMinI,
                            maxLength,
                            isNew,
                            addTime
                    );
                    // candidatesService.insertCandidate(candidate);
                    candidateAddSet.add(candidate);

                    System.out.println(candidate);
                }

            }
        }

        candidatesService.deleteAllCandidates();
        candidatesService.insertCandidates(candidateAddSet);

        long endTime = System.currentTimeMillis() / 1000;
        System.out.println(endTime-startTime);
    }

    private static double getPoint(final double slope, double intercept, int time) {
        double point = (slope*time)+intercept;
        return point;
    }

    private static boolean currentAtLine(final double slope, double intercept, int time, List<Price> prices) {
        double slopePoint = getPoint(slope, intercept, time);
        double slopePointDiff = (prices.get(prices.size()-1).close-slopePoint)/slopePoint;
        boolean atLine = (Math.abs(slopePointDiff)<=PROXIMITY_THRESHOLD);
        //boolean atLine = ((slopePointDiff<=PROXIMITY_THRESHOLD) && (slopePointDiff>=0));

        for (int i = 1; i <= MIN_LAST_ANCHOR_DIST; i++) {
            double approachSlopePoint = getPoint(slope, intercept, time);
            double approachSlopePointDiff = (prices.get(prices.size()-i).low-approachSlopePoint)/approachSlopePoint;
            boolean approachAtLine = (approachSlopePointDiff>=-PROXIMITY_THRESHOLD);
            if (!approachAtLine) {
                return false;
            }
        }

        return atLine;
    }

    private static int[] numRebounds(final double slope, double intercept, int minI, List<Price> prices) {
        int rebounds = 0;
        int lastReboundI = 0;
        int firstReboundI = 15;

        for (int i = 0; i < prices.size()-MIN_LAST_ANCHOR_DIST-10; i++) {
            if (i-lastReboundI<10) {
                continue;
            }

            double slopePoint = getPoint(slope, intercept, i-minI);

            if (Math.abs(prices.get(i).low-slopePoint)/slopePoint<=PROXIMITY_THRESHOLD) {
                if (rebounds == 0) {
                    firstReboundI = i;
                }
                rebounds += 1;
                lastReboundI = i;
            }
        }

        int[] response = {rebounds, firstReboundI};
        return response;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.findCandidates();
    }

    public static CandidateJob getInstance() {
        if (candidate_job_instance == null) {
            candidate_job_instance = new CandidateJob();
        }

        return candidate_job_instance;
    }

}
