package com.example.authservice.ServiceImpl;


import com.example.authservice.dto.AuthorizationDto;
import com.example.authservice.dto.OrganizationAuthorizationDto;
import com.example.authservice.exception.AuthorizationNotFoundException;
import com.example.authservice.exception.OrganizationNotFoundException;
import com.example.authservice.model.Authorization;
import com.example.authservice.model.Organization;
import com.example.authservice.model.OrganizationAuthorization;
import com.example.authservice.repository.OrganizationAuthorizationRepository;
import com.example.authservice.repository.AuthorizationRepository;
import com.example.authservice.repository.OrganizationRepository;
import com.example.authservice.service.AuthorizationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    final
    OrganizationAuthorizationRepository organizationAuthorizationRepository;

    final
    AuthorizationRepository authorizationRepository;

    final
    OrganizationRepository organizationRepository;

    public AuthorizationServiceImpl(OrganizationAuthorizationRepository authorizationOrganizationRepository, AuthorizationRepository authorizationRepository, OrganizationRepository organizationRepository) {
        this.organizationAuthorizationRepository = authorizationOrganizationRepository;
        this.authorizationRepository = authorizationRepository;
        this.organizationRepository = organizationRepository;
    }



    @Transactional
    @Override
    //handel validation of the dto later
    public OrganizationAuthorization addAuthorizationOrganization(OrganizationAuthorizationDto organizationAuthorizationDto) {
        Authorization authorization = authorizationRepository.findById(organizationAuthorizationDto.getAuthorization_id()).orElseThrow(
                AuthorizationNotFoundException::new
        );

        Organization organization = organizationRepository.findById(organizationAuthorizationDto.getOrganization_id()).orElseThrow(
                OrganizationNotFoundException::new
        );

        OrganizationAuthorization organizationAuthorization = new OrganizationAuthorization();
        organizationAuthorization.setAuthorization(authorization);
        organizationAuthorization.setOrganization(organization);
        organizationAuthorization.setDedicatedPaper(organizationAuthorizationDto.getDedicatedPaper());

        return organizationAuthorizationRepository.save(organizationAuthorization);

    }

    @Override
    public void deleteOrganizationAuthorization(Long id) {



        organizationAuthorizationRepository.deleteById(id);

    }

    @Override
    public List<OrganizationAuthorization> getOrganizationAuthorizations(Organization organization) {

        return organizationAuthorizationRepository.findAllByOrganization(organization);

    }

    @Override
    public Authorization addAuthorization(AuthorizationDto authorizationDto) {

        Authorization authorization = new Authorization();

        authorization.setName(authorization.getName());
        authorization.setRequiredPaper(authorization.getRequiredPaper());

        return authorizationRepository.save(authorization);

    }

    @Override
    public Authorization updateAuthorization(Long id , AuthorizationDto authorizationDto) {

        Authorization authorization = this.getAuthorization(id);
        authorization.setId(id);
        authorization.setName(authorizationDto.getName());
        authorization.setRequiredPaper(authorizationDto.getRequiredPaper());
        return authorizationRepository.save(authorization);
    }

    @Override
    public void deleteAuthorization(Long id) {

        organizationAuthorizationRepository.deleteByAuthorizationId(id);
        authorizationRepository.deleteById(id);

    }

    @Override
    public Authorization getAuthorization(Long id) {
        return authorizationRepository.findById(id).orElseThrow(
                AuthorizationNotFoundException::new
        );
    }

    @Override
    public List<Authorization> getAllAuthorizations() {
        return authorizationRepository.findAll();
    }


}
