package com.joaoszczypiordev.sgci.infra.handler;


import com.joaoszczypiordev.sgci.infra.exceptions.InvalidInputException;
import com.joaoszczypiordev.sgci.infra.exceptions.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<StandardError> invalidInputException(InvalidInputException e) {

        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
