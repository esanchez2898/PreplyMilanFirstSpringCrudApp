package com.erick.spring.controller;

import com.erick.spring.exception.DataNotValidateException;
import com.erick.spring.exception.ExistingInstanceException;
import com.erick.spring.exception.InstanceUndefinedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsController {

    @ExceptionHandler(DataNotValidateException.class)
    public ResponseEntity<String> handleNotDataValidationException(DataNotValidateException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistingInstanceException.class)
    public ResponseEntity<String> handleExistingInstanceException(ExistingInstanceException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InstanceUndefinedException.class)
    public ResponseEntity<String> handleInstanceUndefinedException(InstanceUndefinedException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }


}
