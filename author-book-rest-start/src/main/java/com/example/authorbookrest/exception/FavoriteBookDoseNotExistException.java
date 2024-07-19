package com.example.authorbookrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FavoriteBookDoseNotExistException extends RuntimeException {
    public FavoriteBookDoseNotExistException(String message) {
        super(message);
    }
}
