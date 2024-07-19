package com.example.authorbookrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FavoriteBookAlreadyExistException extends RuntimeException {
    public FavoriteBookAlreadyExistException(String message) {
        super(message);
    }
}
