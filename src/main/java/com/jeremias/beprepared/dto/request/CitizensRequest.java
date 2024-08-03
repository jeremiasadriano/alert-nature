package com.jeremias.beprepared.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CitizensRequest {
    @NotBlank
    @Size(min = 9, max = 9)
    private String phone;
    @NotBlank
    private String deviceId;
}
