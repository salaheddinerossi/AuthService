package com.example.authservice.controller;


import com.example.authservice.dto.AuthorizationDto;
import com.example.authservice.dto.OrganizationAuthorizationDto;
import com.example.authservice.model.Authorization;
import com.example.authservice.model.Organization;
import com.example.authservice.model.OrganizationAuthorization;
import com.example.authservice.service.AuthorizationService;
import com.example.authservice.service.OrganizationService;
import com.example.authservice.util.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/authorization")

public class AuthorizationController {

    private final
    AuthorizationService authorizationService;

    private final
    OrganizationService organizationService;


    public AuthorizationController(AuthorizationService authorizationService, OrganizationService organizationService) {
        this.authorizationService = authorizationService;
        this.organizationService = organizationService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllAuthorizations(){
        return ResponseEntity.status(HttpStatus.OK).body(authorizationService.getAllAuthorizations());
    }

    @PostMapping("/")
    public ResponseEntity<?> addAuthorization(@Valid @RequestBody AuthorizationDto authorizationDto){

        Authorization authorization = authorizationService.addAuthorization(authorizationDto);
        return ResponseEntity.status(HttpStatus.OK).body(authorization);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthorization(@Valid @RequestBody AuthorizationDto authorizationDto,@PathVariable Long id){

        Authorization authorization = authorizationService.updateAuthorization(id,authorizationDto);
        return ResponseEntity.status(HttpStatus.OK).body(authorization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorization(@PathVariable Long id){
        authorizationService.deleteAuthorization(id);
        return ResponseEntity.status(HttpStatus.OK).body("authorization deleted");
    }

    @PostMapping("/addAuthorization")
    public ResponseEntity<?> addAuthorizationToOrganization (@Valid @RequestBody OrganizationAuthorizationDto organizationAuthorizationDto){

        OrganizationAuthorization organizationAuthorization = authorizationService.addAuthorizationOrganization(organizationAuthorizationDto);
        return ResponseEntity.status(HttpStatus.OK).body(organizationAuthorization);

    }

    @GetMapping("/organization/{id}")
    public ResponseEntity<?> getAuthorizationsByOrganization(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){

        Organization organization = organizationService.getOrganizationById(id);
        List<OrganizationAuthorization> organizationAuthorizations = authorizationService.getOrganizationAuthorizations(organization);

        if (SecurityUtils.isAdmin(userDetails.getAuthorities())){
            return ResponseEntity.status(HttpStatus.OK).body(organizationAuthorizations);
        }

        if(SecurityUtils.isOrganization(userDetails.getAuthorities())){
            Organization existingOrganization = organizationService.getOrganizationByEmail(userDetails.getUsername());
            if(Objects.equals(existingOrganization.getId(), id)){
                return ResponseEntity.status(HttpStatus.OK).body(organizationAuthorizations);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you don't have the access to this route");

    }


    @DeleteMapping("/organizationAuthorization/{id}")
    public ResponseEntity<?> deleteOrganizationAuthorization (@PathVariable Long id){

        authorizationService.deleteOrganizationAuthorization(id);
        return ResponseEntity.status(HttpStatus.OK).body("the organization authorization has been deleted");

    }

}
