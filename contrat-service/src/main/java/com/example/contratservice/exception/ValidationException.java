package com.example.contratservice.exception;

public class ValidationException extends RuntimeException{

    public ValidationException(String message)
    {
        super(message);
    }
}
