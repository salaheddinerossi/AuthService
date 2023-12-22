package com.example.authservice.service;

import com.example.authservice.dto.AuthorizationDto;
import com.example.authservice.dto.OrganizationAuthorizationDto;
import com.example.authservice.dto.OrganizationDto;
import com.example.authservice.model.Authorization;
import com.example.authservice.model.Organization;
import com.example.authservice.model.OrganizationAuthorization;

import java.util.List;

public interface AuthorizationService {

    public OrganizationAuthorization addAuthorizationOrganization (OrganizationAuthorizationDto organizationAuthorizationDto);

    public void deleteOrganizationAuthorization (Long id);

    public List<OrganizationAuthorization> getOrganizationAuthorizations(Organization organization);

    public Authorization addAuthorization(AuthorizationDto authorizationDto);

    public Authorization updateAuthorization (Long id, AuthorizationDto authorizationDto);

    public void  deleteAuthorization (Long id);

    public Authorization getAuthorization(Long id);

    public List<Authorization> getAllAuthorizations();

}
