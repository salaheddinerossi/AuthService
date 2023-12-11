package com.example.authservice.controller;


import com.example.authservice.dto.OrganizationDto;
import com.example.authservice.model.Organization;
import com.example.authservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Organization updateOrganization(@PathVariable Long id,@RequestBody OrganizationDto organizationDto){
        return organizationService.updateOrganization(id,organizationDto);
    }

    @DeleteMapping("/{id}")
    public String deleteOrganization(@PathVariable Long id){
        organizationService.deleteOrganization(id);
        return "the organization has been deleted";
    }

    @GetMapping("/")
    public List<Organization> getAllOrganizations(){
        return organizationService.findAllOrganizations();
    }

    @PatchMapping("/changepassword")
    public String changePassword(@RequestBody Long id,String newPassword){
        organizationService.changeOrganizationPassword(id,newPassword);
        return "the password has been changed";
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
