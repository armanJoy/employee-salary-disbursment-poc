package com.ibcs.salaryapp.secuirty.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException notValidException) {
        notValidException.printStackTrace();
        String errorMessage = "";
        if (notValidException != null && notValidException.getBindingResult() != null
                && notValidException.getBindingResult().getFieldErrors() != null) {
            List<FieldError> errors = notValidException.getBindingResult().getFieldErrors();
            for (int i = 0; i < errors.size(); i++) {
                FieldError error = errors.get(i);

                errorMessage = errorMessage + ((i > 0) ? ", " : "") + error.getDefaultMessage()
                        + ((i + 1 == errors.size()) ? "." : "");
            }
        }

        final String msg = errorMessage;
        return new ResponseEntity(new HashMap<String, String>() {
            {
                put("msg", msg);
            }

        }, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleInvalidArgument(ValidationException notValidException,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        notValidException.printStackTrace();
        return new ResponseEntity(new HashMap<String, String>() {
            {
                put("msg", notValidException.getMessage());
            }

        }, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException numberformatException) {
        numberformatException.printStackTrace();
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put("msg", numberformatException.getMessage());
        return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {
        nullPointerException.printStackTrace();
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap.put("error", nullPointerException.getMessage());
        return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
    }

}
