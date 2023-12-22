package com.example.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(OldPasswordNotMatchedException.class)
    public ResponseEntity<Object> handleOldPasswordNotMatched(OldPasswordNotMatchedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordSameAsOldException.class)
    public ResponseEntity<Object> handlePasswordSameAsOld(PasswordSameAsOldException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
