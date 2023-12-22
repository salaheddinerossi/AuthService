package com.example.authservice.service;


import com.example.authservice.dto.OrganizationDto;
import com.example.authservice.model.Organization;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {

    public Organization getOrganizationById(Long id);

    Organization registerOrganization(OrganizationDto organizationDto);

    Organization updateOrganization(Long id, OrganizationDto organizationDto);

    void deleteOrganization(Long id);

    List<Organization> findAllOrganizations();

    void changeOrganizationPassword(Long id, String oldPassword,String newPassword);

    void activateOrganizationAccount(Long id);

    void deactivateOrganizationAccount(Long id);

    Organization getOrganizationByEmail(String email);



}
