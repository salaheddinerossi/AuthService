package com.example.authservice.repository;

import com.example.authservice.model.Authorization;
import com.example.authservice.model.Organization;
import com.example.authservice.model.OrganizationAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationAuthorizationRepository extends JpaRepository<OrganizationAuthorization,Long> {
    List<OrganizationAuthorization> findAllByOrganization(Organization organization);

    void deleteByAuthorizationId(Long id);

    void deleteByOrganizationId(Long id);

    Optional<OrganizationAuthorization> findByOrganizationAndAuthorization(Organization organization, Authorization authorization);
}
