package com.example.travauxservice.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message)
    {
        super(message);
    }
}
