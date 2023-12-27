package com.example.authservice.exception;

public class OrganizationNotActiveException extends RuntimeException{

    public OrganizationNotActiveException(){
        super("account not active yet please contact the administration of the website");
    }
}
