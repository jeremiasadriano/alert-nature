package com.jeremias.beprepared.dto.request;

import com.jeremias.beprepared.models.enums.Severity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlertRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String message;
    @NotBlank
    private Severity severity;
}