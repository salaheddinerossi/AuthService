package com.example.authservice.controller;


import com.example.authservice.dto.OrganizationDto;
import com.example.authservice.model.Organization;
import com.example.authservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OrganizationService organizationService;

    @PostMapping("/register")
    public Organization registerOrganization(@RequestBody OrganizationDto organizationDto){
        return organizationService.registerOrganization(organizationDto);
    }

    @GetMapping("/{id}")
    public Organization getOrganizaion(@PathVariable Long id){
        return organizationService.getOrganizationById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(
            @PathVariable Long id,
            @RequestBody OrganizationDto organizationDto,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        System.out.println(userDetails.getAuthorities());

        Organization existingOrganization = organizationService.getOrganizationByEmail(userDetails.getUsername());

        if(Objects.equals(existingOrganization.getId(), id)){
            return ResponseEntity.status(HttpStatus.OK).body(organizationService.updateOrganization(id,organizationDto));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: you are not the account user.");

    }

    @DeleteMapping("/{id}")
    public String deleteOrganization(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){

        organizationService.deleteOrganization(id);
        return "organization has been deleted";
    }

    @GetMapping("/")
    public List<Organization> getAllOrganizations(){
        return organizationService.findAllOrganizations();
    }

    @PatchMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody OrganizationDto organizationDto, @AuthenticationPrincipal UserDetails userDetails) {

        Organization organization = organizationService.getOrganizationByEmail(userDetails.getUsername());
        if (Objects.equals(organization.getId(), organizationDto.getId())) {
            organizationService.changeOrganizationPassword(
                    organizationDto.getId(),
                    organizationDto.getPassword()
            );
            return ResponseEntity.ok("The password has been changed.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: you are not the account user.");
        }
    }

    @PatchMapping("/activite/{id}")
    public String activateAccount(@PathVariable Long id){

        organizationService.activateOrganizationAccount(id);
        return "account has been activated";

    }

    @PatchMapping("/deactivate/{id}")
    public String deactivateAccount(@PathVariable Long id){
        organizationService.deactivateOrganizationAccount(id);
        return "account has been deactivated";
    }


}
