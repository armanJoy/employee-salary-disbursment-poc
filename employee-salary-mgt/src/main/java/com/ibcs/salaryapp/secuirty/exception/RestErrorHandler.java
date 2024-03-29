package com.ibcs.salaryapp.secuirty.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
//        logger.error(ex.getLocalizedMessage(), ex);
//        return RestResponse.builder()
//                .status(400)
//                .message(ex.getMessage())
//                .path(getPath(request))
//                .entity();
//    }
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleStatusException(ResponseStatusException ex, WebRequest request) {
        logger.error(ex.getReason(), ex);
        return handleResponseStatusException(ex, request);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    ResponseEntity<?> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex, WebRequest request) {
        logger.error(ex.getLocalizedMessage(), ex);
        return RestResponse.builder()
                .status(401)
                .message(ex.getMessage())
                .path(getPath(request))
                .entity();
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error(ex.getLocalizedMessage(), ex);
        return handleEveryException(ex, request);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ResponseEntity<?> responseEntity;

        if (!status.isError()) {
            responseEntity = handleStatusException(ex, status, request);
        } else if (INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
            responseEntity = handleEveryException(ex, request);
        } else {
            responseEntity = handleEveryException(ex, request);
        }

        return (ResponseEntity<Object>) responseEntity;
    }

    protected ResponseEntity<RestResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return RestResponse.builder()
                .exception(ex)
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestResponse> handleStatusException(Exception ex, HttpStatus status, WebRequest request) {
        return RestResponse.builder()
                .status(status)
                .message("Execution halted")
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestResponse> handleEveryException(Exception ex, WebRequest request) {
        return RestResponse.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message("Server encountered an error")
                .path(getPath(request))
                .entity();
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).substring(4);
    }

}
