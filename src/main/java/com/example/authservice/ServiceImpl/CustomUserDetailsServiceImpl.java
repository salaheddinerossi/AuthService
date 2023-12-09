package com.example.authservice.ServiceImpl;

import com.example.authservice.model.Admin;
import com.example.authservice.model.Organization;
import com.example.authservice.repository.AdminRepository;
import com.example.authservice.repository.OrganizationRepository;
import com.example.authservice.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final OrganizationRepository organizationRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsServiceImpl(OrganizationRepository organizationRepository,AdminRepository adminRepository) {
        this.organizationRepository = organizationRepository;
        this.adminRepository=adminRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<Organization> organization = organizationRepository.findByEmail(username);

        if(organization.isPresent()){
            return new User(
                    organization.get().getEmail(),
                    organization.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ORGANIZATION"))
            );
        }

        Optional<Admin> admin = adminRepository.findByEmail(username);

        if (admin.isPresent()){
            return new User(
                    admin.get().getEmail(),
                    admin.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );

        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
