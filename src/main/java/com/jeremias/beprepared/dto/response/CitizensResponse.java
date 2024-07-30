package com.jeremias.beprepared.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CitizensResponse {
    private Long id;
    private String phone;
    private String deviceId;
    private String city;
    private String province;
    private boolean verified;
}
