package com.example.authservice.exception;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(){
        super("admin not found");
    }
}
