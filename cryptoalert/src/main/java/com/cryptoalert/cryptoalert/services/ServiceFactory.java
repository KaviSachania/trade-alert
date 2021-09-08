package com.cryptoalert.cryptoalert.services;

import com.cryptoalert.cryptoalert.auth.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

    private static AlertsService alertsService;
    private static CandidatesService candidatesService;
    private static PricesService pricesService;
    private static StocksService stocksService;
    private static UserService userService;

    @Autowired
    public ServiceFactory(AlertsService alertsService,
                          CandidatesService candidatesService,
                          PricesService pricesService,
                          StocksService stocksService,
                          UserService userService) {
        ServiceFactory.alertsService = alertsService;
        ServiceFactory.candidatesService = candidatesService;
        ServiceFactory.pricesService = pricesService;
        ServiceFactory.stocksService = stocksService;
        ServiceFactory.userService = userService;
    }

    public static AlertsService getAlertsService() {
        return alertsService;
    }

    public static CandidatesService getCandidatesService() {
        return candidatesService;
    }

    public static PricesService getPricesService() {
        return pricesService;
    }

    public static StocksService getStocksService() {
        return stocksService;
    }

    public static UserService getUserService() {
        return userService;
    }


}
