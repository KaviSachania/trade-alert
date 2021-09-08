package com.cryptoalert.cryptoalert.jobs;

import com.cryptoalert.cryptoalert.auth.user.AppUser;
import com.cryptoalert.cryptoalert.auth.user.UserService;
import com.cryptoalert.cryptoalert.mappers.Alert;
import com.cryptoalert.cryptoalert.mappers.Candidate;
import com.cryptoalert.cryptoalert.mappers.Stock;
import com.cryptoalert.cryptoalert.services.AlertsService;
import com.cryptoalert.cryptoalert.services.CandidatesService;
import com.cryptoalert.cryptoalert.services.ServiceFactory;
import com.cryptoalert.cryptoalert.services.StocksService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AlertJob implements Job {

    private static AlertJob alert_job_instance = null;

    private static final String ALERT_EMAIL_SUBJECT = "Weekly Trade Alert";

    private static final String WEBSITE_LINK = "https://tradealert.money";

    public AlertJob() {

    }

    public void checkAlerts() {
        AlertsService alertsService = ServiceFactory.getAlertsService();
        CandidatesService candidatesService = ServiceFactory.getCandidatesService();
        StocksService stocksService = ServiceFactory.getStocksService();
        UserService userService = ServiceFactory.getUserService();

        List<AppUser> users = userService.getEnabledUsers();
        List<Candidate> candidates = candidatesService.getNewCandidates();

        Map<String, Stock> stocksById = stocksService.getAllStocks().stream()
                .collect(Collectors.toMap(Stock::getId, Function.identity()));

        for (AppUser user : users) {
            Map<String, List<Candidate>> candidatesByAlertId = new HashMap<String, List<Candidate>>();

            List<Alert> alerts = alertsService.getAlertsByUserId(user.getId());

            for (Alert alert : alerts) {
                List<Candidate> qualifiedCandidates = new ArrayList<Candidate>();

                for (Candidate candidate : candidates) {
                    Stock stock = stocksById.get(candidate.stockId);

                    if (candidate.trend != alert.trend) {
                        continue;
                    }
                    if ((alert.minSlope != null) && (candidate.slope < alert.minSlope)) {
                        continue;
                    }
                    if ((alert.minLength != null) && (candidate.length < alert.minLength)) {
                        continue;
                    }
                    if ((alert.minMarketCap != null) && (stock.marketCap < alert.minMarketCap)) {
                        continue;
                    }

                    qualifiedCandidates.add(candidate);
                }

                candidatesByAlertId.put(alert.id, qualifiedCandidates);
            }

            if (alerts.size() > 0) {
                alertsService.sendAlertEmail(
                        user.getEmail(),
                        ALERT_EMAIL_SUBJECT,
                        this.buildEmail(candidatesByAlertId, stocksById, alerts));
            }
        }

    }

    public String buildEmail(Map<String, List<Candidate>> candidatesByAlertId,
                             Map<String, Stock> stocksById,
                             List<Alert> alerts) {
        String body = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Stocks that reached their support line this week</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi,</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Here is your weekly report on each alert you have set up for stocks that have reached their support line. </p>";

        for (Alert alert : alerts) {
            String minMarketCap = (alert.minMarketCap != null) ? this.prettyMarketCapString(alert.minMarketCap) : "N/A";
            String minSlope = (alert.minSlope != null) ? ((double) Math.round(alert.minSlope * 1000) / 1000) + "%" : "N/A";
            String minLength = (alert.minLength != null) ? alert.minLength + " days" : "N/A";

            body += "<p> Minimum Market Capitalization: " + minMarketCap + "</p>\n" +
                    "<p> Minimum Average Weekly +/-: " + minSlope + "</p>\n" +
                    "<p> Minimum Days: " + minLength + " </p>\n";

            List<Candidate> candidates = candidatesByAlertId.get(alert.id);
            if (candidates.size() == 0) {
                body += "<p>No stocks for this alert.</p><br><br>";
                continue;
            }

            System.out.println(alert.id);

            body += "<table><thead><tr><th>Ticker</th><th>Name</th><th>Average Weekly +/-</th><th>Days</th><th>Market Capitalization</th></tr></thead><tbody>";
            for (Candidate candidate : candidates) {
                Stock stock = stocksById.get(candidate.stockId);
                System.out.println(candidate.id);
                System.out.println(stock.ticker);

                body += "<tr><td>" + stock.ticker + "</td><td>" + stock.name + "</td><td>" +  ((double) Math.round(candidate.slope * 1000) / 1000) + "%</td><td>" + candidate.length + " days</td><td>" + this.prettyMarketCapString(stock.marketCap) + "</td></tr>";
            }

            body += "</tbody></table><br><br>";
        }

        body += "<p>Visit <a href=\"" + WEBSITE_LINK + "\">tradealert.money</a> to view more stocks at their support line.</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";

        return body;
    }

    private String prettyMarketCapString(int marketCap) {
        String amountLabel = "";
        double marketCapDouble = Double.valueOf(marketCap);

        if (marketCap >= 1000000) {
            marketCapDouble /= 1000000;
            amountLabel = "T";
        } else if (marketCap >= 1000) {
            marketCapDouble /= 1000;
            amountLabel = "B";
        } else {
            amountLabel = "M";
        }

        String marketCapString = String.format("%.3g", marketCapDouble);
        if (marketCap < 10) {
            marketCapString = marketCapString.substring(0, marketCapString.length()-3);
        } else if (marketCap < 100) {
            marketCapString = marketCapString.substring(0, marketCapString.length()-2);
        }

        return marketCapString + " " + amountLabel;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.checkAlerts();
    }

    public static AlertJob getInstance() {
        if (alert_job_instance == null) {
            alert_job_instance = new AlertJob();
        }

        return alert_job_instance;
    }

}
