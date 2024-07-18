package com.jeremias.beprepared.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionsModel(HttpStatus status, String message) {
}
