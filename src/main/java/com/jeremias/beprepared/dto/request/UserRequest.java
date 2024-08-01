package com.jeremias.beprepared.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    @Email(message = "The email format isn't recognized", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,}$")
    private String email;
    @Size(min = 8, message = "The password must be at least 8 characters long")
    private String password;
}