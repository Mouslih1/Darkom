package org.example.chatservice.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message)
    {
        super(message);
    }
}
