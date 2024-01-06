package com.example.authservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class OrganizationAuthorizationDto {

    @NotNull(message = "Organization ID is required")
    private Long organization_id;

    @NotNull(message = "Authorization ID is required")
    private Long authorization_id;

    private String dedicatedPaper;

}
