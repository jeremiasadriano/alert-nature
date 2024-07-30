package com.jeremias.beprepared.dto.response;

import com.jeremias.beprepared.models.enums.Severity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertResponse {
    private Long id;
    private String title;
    private String message;
    private boolean status;
    private Severity severity;
    private String city;
    private String province;
}
