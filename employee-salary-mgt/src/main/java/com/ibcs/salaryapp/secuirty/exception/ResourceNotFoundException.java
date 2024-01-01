package com.ibcs.salaryapp.secuirty.exception;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundException extends ResponseStatusException {

  public ResourceNotFoundException() {
    super(NOT_FOUND);
  }

  public ResourceNotFoundException(String reason) {
    super(NOT_FOUND, reason);
  }

  public ResourceNotFoundException(String reason, Throwable cause) {
    super(NOT_FOUND, reason, cause);
  }
}