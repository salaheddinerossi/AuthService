package com.example.authservice.controller;


import com.example.authservice.dto.ChangePasswordDto;
import com.example.authservice.dto.OrganizationDto;
import com.example.authservice.model.Organization;
import com.example.authservice.service.OrganizationService;
import com.example.authservice.util.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    final
    OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/register")
    //completed
    public Organization registerOrganization(@RequestBody OrganizationDto organizationDto){
        return organizationService.registerOrganization(organizationDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizaion(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){
    //completed


        if(SecurityUtils.isAdmin(userDetails.getAuthorities())){
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.getOrganizationById(id));
        }

        if(SecurityUtils.isOrganization(userDetails.getAuthorities())){
            Organization organization = organizationService.getOrganizationByEmail(userDetails.getUsername());
            if(Objects.equals(id, organization.getId())|| SecurityUtils.isAdmin(userDetails.getAuthorities())){
                return ResponseEntity.status(HttpStatus.OK).body(organizationService.getOrganizationById(id));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: you are not the account user.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(
            @PathVariable Long id,
            @RequestBody OrganizationDto organizationDto,
            @AuthenticationPrincipal UserDetails userDetails
    ){

        //completed

        if (SecurityUtils.isAdmin(userDetails.getAuthorities())){

            return ResponseEntity.status(HttpStatus.OK).body(organizationService.updateOrganization(id,organizationDto));

        }

        if(SecurityUtils.isOrganization(userDetails.getAuthorities())){
            Organization existingOrganization = organizationService.getOrganizationByEmail(userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.updateOrganization(existingOrganization.getId(),organizationDto));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: you are not the account user.");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){

        if (SecurityUtils.isAdmin(userDetails.getAuthorities())){
            organizationService.deleteOrganization(id);
            ResponseEntity.status(HttpStatus.OK).body("user has been deleted");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("only admin can delete the accout ");
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrganizations(@AuthenticationPrincipal UserDetails userDetails){
         //only for admin

        if (SecurityUtils.isAdmin(userDetails.getAuthorities())){
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.findAllOrganizations());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("only admin can access all organizations");
    }

    @PatchMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (SecurityUtils.isOrganization(userDetails.getAuthorities())){
            Organization organization = organizationService.getOrganizationByEmail(userDetails.getUsername());
            organizationService.changeOrganizationPassword(organization.getId(),changePasswordDto.getOldPassword(),changePasswordDto.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body("password has been changedd");

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("only the owner of the user can change the password");
    }

    @PatchMapping("/activite/{id}")
    public ResponseEntity<?> activateAccount(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){

        if (SecurityUtils.isAdmin(userDetails.getAuthorities())){
            organizationService.activateOrganizationAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body("account has been activited");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("account not authorized");

    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateAccount(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){

        if (SecurityUtils.isAdmin(userDetails.getAuthorities())){
            organizationService.deactivateOrganizationAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body("account has been deactivated");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("only admin can effect this operation");
    }

}
