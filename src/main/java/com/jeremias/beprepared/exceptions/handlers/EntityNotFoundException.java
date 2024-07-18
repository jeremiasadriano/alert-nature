package com.jeremias.beprepared.exceptions.handlers;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
