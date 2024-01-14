package com.ibcs.salaryapp.secuirty.exception;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@ControllerAdvice
public class ApiExceptionHandler extends ExceptionHandlerExceptionResolver {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String msg = "";
        if (ex.getAllErrors() != null && ex.getAllErrors().size() > 0) {
            for (ObjectError allError : ex.getAllErrors()) {
                msg = msg.concat(allError.getDefaultMessage());
            }
        }
        logger.error(msg, ex);
        return ResponseEntity
                .status(400)
                .body(new ResponseMessage(msg));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ResponseMessage> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String msg = ex.getConstraintViolations().stream().map(item -> item.getMessage()).collect(Collectors.joining(", "));
        logger.error(msg, ex);
        System.out.println(msg);
        return ResponseEntity
                .status(400)
                .body(new ResponseMessage(msg));
    }

}
