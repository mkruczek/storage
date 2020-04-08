package com.mkruczek.storage.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FailedDownloadResourceException extends RuntimeException {
    public FailedDownloadResourceException(String message) {
        super(message);
    }
}
