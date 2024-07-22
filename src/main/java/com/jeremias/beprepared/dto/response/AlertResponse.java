package com.jeremias.beprepared.dto.response;

import com.jeremias.beprepared.models.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlertResponse {
    private Long id;
    private String title;
    private String message;
    private boolean status;
    private Severity severity;
    private String city;
    private String province;
}
