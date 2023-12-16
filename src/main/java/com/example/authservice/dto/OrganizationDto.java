package com.example.authservice.dto;


import lombok.Data;

@Data
public class OrganizationDto {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String password;
    private String description;
    private String documents;
    private Boolean isActive;

}
