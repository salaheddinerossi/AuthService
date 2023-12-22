package com.example.authservice.exception;

public class AuthorizationNotFoundException extends RuntimeException{
    public AuthorizationNotFoundException(){
        super("Authorization not found!");
    }
}
