package com.mkruczek.storage.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FailedSaveResourceException extends RuntimeException {
    public FailedSaveResourceException(String message) {
        super(message);
    }
}
