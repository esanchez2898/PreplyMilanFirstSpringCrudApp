package com.erick.spring.exception;

public class ExistingInstanceException extends RuntimeException {

    public ExistingInstanceException(String message) {
        super(message);
    }
}
