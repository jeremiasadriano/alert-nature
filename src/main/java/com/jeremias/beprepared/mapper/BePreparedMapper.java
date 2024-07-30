package com.jeremias.beprepared.mapper;

import com.jeremias.beprepared.dto.response.AlertResponse;
import com.jeremias.beprepared.dto.response.CitizensResponse;
import com.jeremias.beprepared.models.Alert;
import com.jeremias.beprepared.models.Citizens;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BePreparedMapper {
    public AlertResponse map(Alert alert) {
        return AlertResponse.builder()
                .id(alert.getId())
                .title(alert.getTitle())
                .message(alert.getMessage())
                .status(alert.isStatus())
                .severity(alert.getSeverity())
                .city(alert.getCity().getDesignation())
                .province(alert.getProvince().getDesignation())
                .build();
    }

    public CitizensResponse map(Citizens citizens) {
        return CitizensResponse.builder()
                .id(citizens.getId())
                .phone(citizens.getPhone())
                .deviceId(citizens.getDeviceId())
                .city(citizens.getCity().getDesignation())
                .province(citizens.getCity().getProvince().getDesignation())
                .verified(citizens.isVerified())
                .build();
    }

    public List<CitizensResponse> map(List<Citizens> citizens) {
        return citizens.stream().map(this::map).toList();
    }
}