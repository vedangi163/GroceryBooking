package com.groceryBooking;

import org.springframework.http.HttpStatus;

public class ValidatingException extends RuntimeException {
    HttpStatus code;
    public ValidatingException(String msg, HttpStatus status) {
        super(msg);
        this.code = status;
    }
}
