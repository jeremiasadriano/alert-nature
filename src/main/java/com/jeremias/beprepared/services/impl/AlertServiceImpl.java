package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.infra.SmsSender;
import com.jeremias.beprepared.models.Alert;
import com.jeremias.beprepared.models.Citizens;
import com.jeremias.beprepared.repositories.AlertRepository;
import com.jeremias.beprepared.services.AlertService;
import com.jeremias.beprepared.services.CitizensService;
import com.jeremias.beprepared.services.CityService;
import com.jeremias.beprepared.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;
    private final CityService cityService;
    private final ProvinceService provinceService;
    private final CitizensService citizensService;
    private final SmsSender smsSender;

    @Override
    @Transactional
    public String createAlert(Alert alert, Long cityId, Long provinceId) {
        try {
            var city = cityService.getCityById(cityId);
            var province = provinceService.getProvinceById(provinceId);
            alert.setStatus(false);
            alert.setCity(city);
            alert.setProvince(province);
            this.alertRepository.save(alert);
        } catch (Exception e) {
            log.error("Error creating the alert ", e);
        }
        return "Alert created successfully";
    }

    private List<Alert> findAllAlerts(Long cityId, Long provinceId, Boolean status) {
        if (cityId != null) {
            return (status == null) ? this.alertRepository.findAllByCityId(cityId) : this.alertRepository.findAllByCityIdAndStatus(cityId, status);
        } else if (provinceId != null) {
            return (status == null) ? this.alertRepository.findAllByProvinceId(provinceId) : this.alertRepository.findAllByProvinceIdAndStatus(provinceId, status);
        } else {
            return (status == null) ? this.alertRepository.findAll() : this.alertRepository.findAllByStatus(status);
        }
    }

    @Override
    public List<Alert> getAllAlerts(Boolean status) {
        return findAllAlerts(null, null, status);
    }

    @Override
    public List<Alert> getAlertsByCityId(Long cityId, Boolean status) {
        return findAllAlerts(cityId, null, status);
    }

    @Override
    public List<Alert> getAlertsByProvinceId(Long provinceId, Boolean status) {
        return findAllAlerts(null, provinceId, status);
    }

    @Override
    public Alert getAlertById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert not Found!"));
    }

    @Override
    @Transactional
    public String activeAlert(Long alertId) {
        Alert alert = this.getAlertById(alertId);
        List<Citizens> citizens = this.citizensService.getAllCitizensByCityId(alert.getCity().getId());
        StringBuilder message = new StringBuilder();
        message.append(alert.getTitle()).append("\n");
        message.append(alert.getProvince().getDesignation()).append(" city of ");
        message.append(alert.getCity().getDesignation()).append("\n");
        message.append(alert.getMessage()).append(" ");
        message.append(alert.getSeverity().name());

        for (Citizens citizen : citizens) {
            smsSender.send(citizen.getPhone(), message.toString());
        }
        alert.setStatus(true);
        this.alertRepository.save(alert);
        return "Status updated successfully";
    }
}
