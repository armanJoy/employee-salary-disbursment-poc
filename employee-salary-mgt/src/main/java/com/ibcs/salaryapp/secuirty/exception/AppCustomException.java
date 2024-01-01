package com.ibcs.salaryapp.secuirty.exception;

import java.io.Serializable;

public class AppCustomException extends RuntimeException implements Serializable {

    public AppCustomException() {
    }

    public AppCustomException(String message) {
        super(message);
    }

}
