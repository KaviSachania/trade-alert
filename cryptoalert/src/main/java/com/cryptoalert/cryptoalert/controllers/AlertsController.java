package com.cryptoalert.cryptoalert.controllers;

import com.cryptoalert.cryptoalert.auth.user.AppUser;
import com.cryptoalert.cryptoalert.auth.user.UserService;
import com.cryptoalert.cryptoalert.controllers.responseEntities.ResponseMessageEntity;
import com.cryptoalert.cryptoalert.controllers.responseEntities.ResponseMessageListEntity;
import com.cryptoalert.cryptoalert.mappers.Alert;
import com.cryptoalert.cryptoalert.services.AlertsService;
import com.cryptoalert.cryptoalert.services.PricesService;
import com.cryptoalert.cryptoalert.services.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class AlertsController {

    private final AlertsService alertsService;
    private final StocksService stocksService;
    private final PricesService pricesService;
    private final UserService userService;

    @Autowired
    public AlertsController(AlertsService alertsService,
                            PricesService pricesService,
                            StocksService stocksService,
                            UserService userService) {
        this.alertsService = alertsService;
        this.pricesService = pricesService;
        this.stocksService = stocksService;
        this.userService = userService;
    }

    @RequestMapping(
            path="/alerts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageListEntity<Alert>> getAllAlerts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication == null) || (authentication.getName().equals("anonymousUser"))){
            System.out.println("Could not find authentication for user.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        AppUser user = userService.getUserByEmail(authentication.getName());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Alert> alerts = alertsService.getAlertsByUserId(user.getId());
        ResponseMessageListEntity<Alert> message =
                new ResponseMessageListEntity<Alert>(alerts);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(
            path="/alerts/{alertId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageEntity<Alert>> getAlertById(
            @PathVariable("alertId") final String alertId) {
        Alert alert = alertsService.getAlertById(alertId);
        if (alert == null) {
            System.out.println("Could not find alert.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ResponseMessageEntity<Alert> message = new ResponseMessageEntity<Alert>(alert);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(
            path="/alerts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageEntity<Alert>> addAlert(
            @RequestBody Alert alert) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if ((authentication == null) || (authentication.getName().equals("anonymousUser"))){
            System.out.println(authentication.getName());
            System.out.println("Could not find authentication for user.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        AppUser appUser = userService.getUserByEmail(authentication.getName());
        if (appUser == null) {
            System.out.println("Could not find user by email.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Alert newAlert = new Alert(
                UUID.randomUUID().toString(),
                alert.trend,
                alert.minSlope,
                alert.minLength,
                alert.minMarketCap,
                appUser
        );
        alertsService.insertAlert(newAlert);

        ResponseMessageEntity<Alert> message = new ResponseMessageEntity<Alert>(newAlert);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /*

    @RequestMapping(
            path="/alerts/{alertId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageWithRelatablesEntity<Alert>> updateAlertById(
            @PathVariable final String alertId,
            @RequestBody final Alert alert) {
        Alert originalAlert = alertsService.getAlertById(alertId);
        if (originalAlert == null) {
            System.out.println("Could not find alert.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Stock stock = stocksService.getStockById(originalAlert.stockId);
        if (stock == null) {
            System.out.println("Could not find stock for alert.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        final int updated = alertsService.updateAlert(alert);
        if (updated <= 0) {
            System.out.println("Failed to update alert.");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Price price = pricesService.getCurrentPriceByStockId(originalAlert.stockId);

        ResponseMessageWithRelatablesEntity<Alert> message = new ResponseMessageWithRelatablesEntity<Alert>(alert);
        message.addRelatable(stock);
        message.addRelatable(price);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    */

    @RequestMapping(
            path="/alerts/{alertId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteAlertById(
            @PathVariable final String alertId) {
        Alert alert = alertsService.getAlertById(alertId);
        if (alert == null) {
            System.out.println("Could not find alert.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        final int deleted = alertsService.deleteAlert(alert);
        if (deleted <= 0) {
            System.out.println("Failed to delete alert.");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return HttpStatus.NO_CONTENT;
    }

}
