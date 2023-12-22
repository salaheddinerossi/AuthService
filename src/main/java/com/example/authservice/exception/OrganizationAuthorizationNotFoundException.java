package com.example.authservice.exception;

public class OrganizationAuthorizationNotFoundException extends RuntimeException {

    public OrganizationAuthorizationNotFoundException(){
        super("Organization Authorization Not Found");
    }
}
