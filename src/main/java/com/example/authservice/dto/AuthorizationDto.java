package com.example.authservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AuthorizationDto {

    private Long id;


    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Required paper description is required")
    private String requiredPaper;


}
