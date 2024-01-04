package com.ibcs.salaryapp.secuirty.exception;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class InvalidOperationException extends ResponseStatusException {

    public InvalidOperationException() {
        super(INTERNAL_SERVER_ERROR);
    }

    public InvalidOperationException(String reason) {
        super(INTERNAL_SERVER_ERROR, reason);
    }

    public InvalidOperationException(String reason, Throwable cause) {
        super(INTERNAL_SERVER_ERROR, reason, cause);
    }

}
