package com.example.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(OrganizationNotActiveException.class)
    public ResponseEntity<Object> handelOrganizationNotActiveException(OrganizationNotActiveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(PasswordSameAsOldException.class)
    public ResponseEntity<Object> handlePasswordSameAsOld(PasswordSameAsOldException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationNotFoundException.class)
    public ResponseEntity<Object> handelAuthorizationNotFound(AuthorizationNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrganizationAuthorizationNotFoundException.class)
    public ResponseEntity<Object> handelAuthorizationOrganizationNotFound(OrganizationAuthorizationNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<Object> handelOrganizationNotFound(OrganizationNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationAlreadyExistsException.class)
    public ResponseEntity<Object> handelAuthorizationAlreadyExists(AuthorizationAlreadyExistsException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<Object> handelAdminNotFoundException(AdminNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



}
