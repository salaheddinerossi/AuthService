package com.example.authservice.ServiceImpl;

import com.example.authservice.dto.OrganizationDto;
import com.example.authservice.exception.EmailAlreadyUsedException;
import com.example.authservice.exception.OldPasswordNotMatchedException;
import com.example.authservice.exception.OrganizationNotFoundException;
import com.example.authservice.exception.PasswordSameAsOldException;
import com.example.authservice.model.Organization;
import com.example.authservice.repository.OrganizationAuthorizationRepository;
import com.example.authservice.repository.OrganizationRepository;
import com.example.authservice.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    private final OrganizationAuthorizationRepository organizationAuthorizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder, OrganizationAuthorizationRepository organizationAuthorizationRepository){
        this.organizationRepository=organizationRepository;
        this.passwordEncoder= passwordEncoder;
        this.organizationAuthorizationRepository = organizationAuthorizationRepository;
    }

    @Override
    public Organization getOrganizationById(Long id) {

        return organizationRepository.findById(id)
                .orElseThrow(
                        OrganizationNotFoundException::new
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

        Organization organization = getOrganizationById(id);


        if(!organizationDto.getEmail().equals(organization.getEmail())){
            organizationRepository.findByEmail(organizationDto.getEmail()).ifPresent(org -> {
                throw new EmailAlreadyUsedException("Email already in use: " + organizationDto.getEmail());
            });
        }

        organization.setAddress(organizationDto.getAddress());
        organization.setDocuments(organizationDto.getDocuments());
        organization.setDescription(organizationDto.getDescription());
        organization.setName(organizationDto.getName());
        organization.setEmail(organizationDto.getEmail());
        organization.setPassword(passwordEncoder.encode(organizationDto.getPassword()));
        organization.setIsActive(organizationDto.getIsActive());
        return organizationRepository.save(organization);
    }

    @Transactional
    @Override
    public void deleteOrganization(Long id) {

        organizationAuthorizationRepository.deleteByOrganizationId(id);
        Organization organization=getOrganizationById(id);
        organizationRepository.delete(organization);
    }

    @Override
    public List<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public void changeOrganizationPassword(Long id, String oldPassword,String newPassword) {

        System.out.println(passwordEncoder.encode(oldPassword));

        Organization organization = this.getOrganizationById(id);

        if (!passwordEncoder.matches(oldPassword,organization.getPassword())){
            throw new OldPasswordNotMatchedException();
        }
        if (passwordEncoder.matches(newPassword,organization.getPassword())){
            throw new PasswordSameAsOldException();
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        organization.setPassword(encodedPassword);
        organizationRepository.save(organization);
    }

    @Override
    public void activateOrganizationAccount(Long id) {
        Organization organization = getOrganizationById(id);
        organization.setIsActive(true);
        organizationRepository.save(organization);
    }

    @Override
    public void deactivateOrganizationAccount(Long id) {
        Organization organization = getOrganizationById(id);
        organization.setIsActive(false);
        organizationRepository.save(organization);

    }

    @Override
    public Organization getOrganizationByEmail(String email) {
        return organizationRepository.findByEmail(email).orElseThrow(
                RuntimeException::new
        );
    }
}
