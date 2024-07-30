package com.jeremias.beprepared.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    @Size(min = 8)
    private String password;
}