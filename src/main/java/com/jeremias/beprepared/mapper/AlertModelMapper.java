package com.jeremias.beprepared.mapper;

import com.jeremias.beprepared.dto.response.AlertResponse;
import com.jeremias.beprepared.models.Alert;
import org.springframework.stereotype.Component;

@Component
public class AlertModelMapper {
    public AlertResponse map(Alert alert) {
        return new AlertResponse(alert.getId(),
                alert.getTitle(),
                alert.getMessage(),
                alert.isStatus(),
                alert.getSeverity(),
                alert.getCity().getDesignation(),
                alert.getProvince().getDesignation()
        );

    }
}
