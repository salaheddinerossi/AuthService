package com.example.authservice.exception;

public class AuthorizationAlreadyExistsException extends RuntimeException{

    public AuthorizationAlreadyExistsException(){
        super("authorization already exists for this organization");
    }

}
