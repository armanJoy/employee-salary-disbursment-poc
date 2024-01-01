package com.ibcs.salaryapp.secuirty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 5251981966366483013L;
    private final String message;
    public CustomException(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    @RestControllerAdvice
    public static class ApplicationExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException notValidException){
             Map<String,String> errorMap = new HashMap<String,String>();
            notValidException.getBindingResult().getFieldErrors().forEach(error->{
                errorMap.put(error.getField(),error.getDefaultMessage());
            });
            return errorMap;
        }
        @ExceptionHandler(NumberFormatException.class)
        public String handleNumberFormatException(NumberFormatException numberformatException){
            Map<String,String> errorMap = new HashMap<String,String>();
            return numberformatException.getMessage();
            }
        @ExceptionHandler(NullPointerException.class)
        public String handleNullPointerException(NullPointerException nullPointerException){
            Map<String,String> errorMap = new HashMap<String,String>();
            return nullPointerException.getMessage();
        }
       }
}
