package com.jeremias.beprepared.exceptions.handlers;

public class EntityBadRequestException extends RuntimeException {
    public EntityBadRequestException(String message) {
        super(message);
    }
}
