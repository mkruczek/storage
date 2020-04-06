package com.mkruczek.storage.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    protected Integer status;
    protected String message;
}
