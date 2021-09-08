package com.cryptoalert.cryptoalert.services;

import com.cryptoalert.cryptoalert.email.AwsSesService;
import com.cryptoalert.cryptoalert.mappers.Alert;
import com.cryptoalert.cryptoalert.repositories.AlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class AlertsService {

    private final AlertsRepository alertsRepository;
    private final AwsSesService awsSesService;

    @Autowired
    public AlertsService(AlertsRepository alertsRepository, AwsSesService awsSesService) {
        this.alertsRepository = alertsRepository;
        this.awsSesService = awsSesService;
    }

    public List<Alert> getAllAlerts() {
        return alertsRepository.getAllAlerts();
    }

    public List<Alert> getAlertsByUserId(Long userId) {
        return alertsRepository.getAlertsByUserId(userId);
    }

    public Alert getAlertById(String alertId) {
        return alertsRepository.getAlertById(alertId);
    }

    public void insertAlert(Alert alert) {
        alertsRepository.save(alert);
    }

    public int updateAlert(Alert alert) {
        return alertsRepository.updateAlert(alert.id, alert.trend, alert.minSlope, alert.minLength, alert.minMarketCap);
    }

    public int deleteAlert(Alert alert) {
        return alertsRepository.deleteAlert(alert.id);
    }

    public int deleteAlertsByUserId(Long userId) {
        return alertsRepository.deleteAlertsByUserId(userId);
    }

    public void sendAlertEmail(String email, String subject, String body) {
        awsSesService.sendEmail(email, subject, body);
    }

}
