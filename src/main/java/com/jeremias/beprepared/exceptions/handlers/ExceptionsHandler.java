package com.jeremias.beprepared.exceptions.handlers;

import com.jeremias.beprepared.exceptions.StandardErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    private final StandardErrorResponse response;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardErrorResponse> EntityNotFoundException(EntityNotFoundException notFound, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        response.setTitle(notFound.getMessage());
        response.setCode(httpStatus.value());
        response.setStatus(httpStatus);
        response.setPath(request.getContextPath());
        response.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(EntityBadRequestException.class)
    public ResponseEntity<StandardErrorResponse> EntityNotNullException(EntityBadRequestException notNullException, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        response.setTitle(notNullException.getMessage());
        response.setCode(httpStatus.value());
        response.setStatus(httpStatus);
        response.setPath(request.getContextPath());
        response.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<StandardErrorResponse> EntityAlreadyExistException(EntityConflictException existException, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        response.setTitle(existException.getMessage());
        response.setCode(httpStatus.value());
        response.setStatus(httpStatus);
        response.setPath(request.getContextPath());
        response.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<StandardErrorResponse.ValidationError> validationErrors = new ArrayList<>();

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String field = ((FieldError) objectError).getField();
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            validationErrors.add(new StandardErrorResponse.ValidationError(field, message));
        }
        response.setTitle("ERROR VALIDATING SOME FIELDS!");
        response.setCode(httpStatus.value());
        response.setStatus(httpStatus);
        response.setPath(request.getContextPath());
        response.setFields(validationErrors);
        response.setTimestamp(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, response, headers, httpStatus, request);
    }
}
