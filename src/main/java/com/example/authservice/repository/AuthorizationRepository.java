package com.example.authservice.repository;

import com.example.authservice.model.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization,Long> {
}
