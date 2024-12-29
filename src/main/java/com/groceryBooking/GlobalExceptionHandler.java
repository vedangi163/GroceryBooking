package com.groceryBooking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidatingException.class)
    public ResponseEntity<String> handleUserNotFound(ValidatingException ex) {
        return ResponseEntity.status(ex.code).body(ex.getMessage());
    }
}
