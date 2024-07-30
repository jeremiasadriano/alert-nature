package com.jeremias.beprepared.exceptions.handlers;

import com.jeremias.beprepared.exceptions.ExceptionsModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionsModel> EntityNotFoundException(EntityNotFoundException notFound) {
        var exceptionsModel = new ExceptionsModel(HttpStatus.NOT_FOUND, notFound.getMessage());
        return new ResponseEntity<>(exceptionsModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityBadRequestException.class)
    public ResponseEntity<ExceptionsModel> EntityNotNullException(EntityBadRequestException notNullException) {
        var exceptionsModel = new ExceptionsModel(HttpStatus.BAD_REQUEST, notNullException.getMessage());
        return new ResponseEntity<>(exceptionsModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ExceptionsModel> EntityAlreadyExistException(EntityConflictException existException) {
        var exceptionsModel = new ExceptionsModel(HttpStatus.CONFLICT, existException.getMessage());
        return new ResponseEntity<>(exceptionsModel, HttpStatus.CONFLICT);
    }
}
