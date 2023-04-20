package com.completablefuture.advice;

import com.completablefuture.exception.UserInfoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception exception){
        return Map.of("errorMessage", exception.getMessage());
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserInfoNotFoundException.class)
    public Map<String, String> handleUserInfoNotFoundException(Exception exception){
        return Map.of("errorMessage", exception.getMessage());
    }
}
