package com.jeremias.beprepared.exceptions.handlers;

public class EntityNotNullException extends RuntimeException {
    public EntityNotNullException(String message) {
        super(message);
    }
}
