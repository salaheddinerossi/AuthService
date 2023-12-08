package com.example.authservice.repository;

import com.example.authservice.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    Optional<Organization> findByEmail(String email);
}
