package com.mkruczek.storage.exception;

import com.mkruczek.storage.exception.exceptions.AddressNotFoundException;
import com.mkruczek.storage.exception.exceptions.FailedSaveResourceException;
import com.mkruczek.storage.exception.exceptions.ResourceNotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(Exception ex) {
        ExceptionResponse er = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<ExceptionResponse>(er, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FailedSaveResourceException.class)
    public ResponseEntity<ExceptionResponse> handleFailedSaveResourceException(Exception ex) {
        ExceptionResponse er = ExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<ExceptionResponse>(er, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AddressNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAddressNotFoundException(Exception ex) {
        ExceptionResponse er = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<ExceptionResponse>(er, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileSizeLimitExceededException.class)
    public ResponseEntity<ExceptionResponse> FileSizeLimitExceededException(Exception ex) {
        ExceptionResponse er = ExceptionResponse.builder()
                .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<ExceptionResponse>(er, new HttpHeaders(), HttpStatus.PAYLOAD_TOO_LARGE);
    }


}
