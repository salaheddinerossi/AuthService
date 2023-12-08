package com.example.authservice.ServiceImpl;

import com.example.authservice.dto.OrganizationDto;
import com.example.authservice.exception.EmailAlreadyUsedException;
import com.example.authservice.model.Organization;
import com.example.authservice.repository.OrganizationRepository;
import com.example.authservice.service.OrganizationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository,PasswordEncoder passwordEncoder){
        this.organizationRepository=organizationRepository;
        this.passwordEncoder= passwordEncoder;
    }

    @Override
    public Organization getOrganizationById(Long id) {

        return organizationRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Organization not found" +id)
                );
    }

    @Override
    public Organization registerOrganization(OrganizationDto organizationDto) {
        organizationRepository.findByEmail(organizationDto.getEmail()).ifPresent(org -> {
            throw new EmailAlreadyUsedException("Email already in use: " + organizationDto.getEmail());
        });
        Organization organization = new Organization();
        organization.setAddress(organizationDto.getAddress());
        organization.setDocuments(organizationDto.getDocuments());
        organization.setDescription(organizationDto.getDescription());
        organization.setName(organizationDto.getName());
        organization.setEmail(organizationDto.getEmail());
        organization.setPassword(passwordEncoder.encode(organizationDto.getPassword()));
        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Long id, OrganizationDto organizationDto) {
        organizationRepository.findByEmail(organizationDto.getEmail()).ifPresent(org -> {
            throw new EmailAlreadyUsedException("Email already in use: " + organizationDto.getEmail());
        });

        Organization organization = new Organization();
        organization.setAddress(organizationDto.getAddress());
        organization.setDocuments(organizationDto.getDocuments());
        organization.setDescription(organizationDto.getDescription());
        organization.setName(organizationDto.getName());
        organization.setEmail(organizationDto.getEmail());
        organization.setPassword(passwordEncoder.encode(organizationDto.getPassword()));
        organization.setIsActive(organizationDto.getIsActive());
        return organizationRepository.save(organization);
    }

    @Override
    public void deleteOrganization(Long id) {
        Organization organization=getOrganizationById(id);
        organizationRepository.delete(organization);
    }

    @Override
    public List<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public void changeOrganizationPassword(Long id, String newPassword) {
        Organization organization = getOrganizationById(id);
        String encodedPassword = passwordEncoder.encode(newPassword);
        organization.setPassword(encodedPassword);
    }

    @Override
    public void activateOrganizationAccount(Long id) {
        Organization organization = getOrganizationById(id);
        organization.setIsActive(true);
    }

    @Override
    public void deactivateOrganizationAccount(Long id) {
        Organization organization = getOrganizationById(id);
        organization.setIsActive(false);

    }

}
