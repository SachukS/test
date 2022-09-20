package com.sachuk.TestTask.controller;

import com.sachuk.TestTask.exception.ResourceAlreadyExistException;
import com.sachuk.TestTask.exception.ResourceNotFoundException;
import com.sachuk.TestTask.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handle(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handle(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(e.getRootCause()!=null?e.getRootCause().getMessage():e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<String> handle(ResourceAlreadyExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handle(UserAlreadyExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
