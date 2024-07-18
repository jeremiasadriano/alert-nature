package com.jeremias.beprepared.exceptions.handlers;

import com.jeremias.beprepared.exceptions.ExceptionsModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    ExceptionsModel exceptionsModel;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionsModel> EntityNotFoundException(EntityNotFoundException notFound) {
        exceptionsModel = new ExceptionsModel(HttpStatus.NOT_FOUND, notFound.getMessage());
        return new ResponseEntity<>(exceptionsModel, HttpStatus.NOT_FOUND);
    }
}
