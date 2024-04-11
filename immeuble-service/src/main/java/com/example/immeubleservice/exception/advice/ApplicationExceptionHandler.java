package com.example.immeubleservice.exception.advice;

import com.example.immeubleservice.exception.Error;
import com.example.immeubleservice.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> hanldeBusinessException(NotFoundException exception)
    {
        Error error = new Error(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return errorMap;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException constraintViolationException) {
            if (Objects.equals(constraintViolationException.getConstraintName(), "uk_aosmso3y0gkvtgngmls5eplov")) {
                String errorMessage = "Violation de contrainte unique : cette reference immeuble existe déjà.";
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Une erreur est survenue lors du traitement de votre requête.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
