package com.jeremias.beprepared.services;

import com.jeremias.beprepared.models.Alert;

import java.util.List;

public interface AlertService {
    String createAlert(Alert alert, Long cityId, Long provinceId);

    List<Alert> getAllAlerts();

    List<Alert> getAllAlertsByStatus(Boolean status);

    List<Alert> getAlertsByCityId(Long cityId, Boolean status);

    List<Alert> getAlertsByProvinceId(Long provinceId, Boolean status);

    Alert getAlertById(Long id);

    String activeAlert(Long alertId);
}
