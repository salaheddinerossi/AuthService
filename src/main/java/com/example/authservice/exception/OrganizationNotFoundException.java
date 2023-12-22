package com.example.authservice.exception;

import com.example.authservice.model.OrganizationAuthorization;

public class OrganizationNotFoundException extends RuntimeException{

    public OrganizationNotFoundException(){
        super("organization not found");
    }
}
