package com.example.authservice.repository;

import com.example.authservice.model.Organization;
import com.example.authservice.model.OrganizationAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationAuthorizationRepository extends JpaRepository<OrganizationAuthorization,Long> {
    List<OrganizationAuthorization> findAllByOrganization(Organization organization);

    void deleteByAuthorizationId(Long id);

    void deleteByOrganizationId(Long id);
}
