package com.mkruczek.storage.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException(String message) {
        super(message);
    }
}
