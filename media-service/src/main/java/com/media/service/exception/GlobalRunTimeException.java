package com.media.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalRunTimeException extends RuntimeException{
    private final HttpStatus status ;

    public GlobalRunTimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
