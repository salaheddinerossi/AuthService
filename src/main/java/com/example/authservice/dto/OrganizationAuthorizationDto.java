package com.example.authservice.dto;


import lombok.Data;

@Data
public class OrganizationAuthorizationDto {

    private Long organization_id;

    private Long authorization_id;

    private String dedicatedPaper;

}
