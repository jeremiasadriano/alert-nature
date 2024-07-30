package com.jeremias.beprepared.exceptions.handlers;

public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String message) {
        super(message);
    }
}
