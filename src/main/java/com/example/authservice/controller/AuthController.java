package com.example.authservice.controller;

import com.example.authservice.exception.AdminNotFoundException;
import com.example.authservice.exception.OrganizationNotActiveException;
import com.example.authservice.exception.OrganizationNotFoundException;
import com.example.authservice.model.Admin;
import com.example.authservice.model.Organization;
import com.example.authservice.response.UserDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.authservice.dto.LoginDto;
import com.example.authservice.security.JwtTokenUtil;
import com.example.authservice.response.JwtResponse;
import com.example.authservice.repository.OrganizationRepository;
import com.example.authservice.repository.AdminRepository;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final OrganizationRepository organizationRepository;

    private final AdminRepository adminRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, OrganizationRepository organizationRepository, AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.organizationRepository = organizationRepository;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/organization/login")
    public ResponseEntity<?> createOrganizationAuthenticationToken(@Valid @RequestBody LoginDto loginDto) {
        try {
            authenticate(loginDto.getEmail(), loginDto.getPassword());
            if (organizationRepository.findByEmail(loginDto.getEmail()).isPresent()) {
                final String token = jwtTokenUtil.generateToken(loginDto.getEmail(), "ROLE_ORGANIZATION");
                Organization organization = organizationRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                        OrganizationNotFoundException::new
                );

                if (!organization.getIsActive()){
                    throw new OrganizationNotActiveException();
                }

                return ResponseEntity.ok(new JwtResponse(token,organization.getId(),organization.getName(),"organization"));
            } else {
                throw new BadCredentialsException("Invalid organization credentials");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> createAdminAuthenticationToken(@Valid @RequestBody LoginDto loginDto) {
        try {
            authenticate(loginDto.getEmail(), loginDto.getPassword());
            if (adminRepository.findByEmail(loginDto.getEmail()).isPresent()) {
                Admin admin = adminRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                        AdminNotFoundException::new
                );
                final String token = jwtTokenUtil.generateToken(loginDto.getEmail(), "ROLE_ADMIN");
                return ResponseEntity.ok(new JwtResponse(token,admin.getId(), admin.getName(),"admin"));
            } else {
                throw new BadCredentialsException("Invalid admin credentials");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            String role = userDetails.getAuthorities().isEmpty() ? null :
                        userDetails.getAuthorities().iterator().next().getAuthority();

            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
            userDetailsResponse.setEmail(userDetails.getUsername());
            userDetailsResponse.setRole(role);
            return ResponseEntity.ok(userDetailsResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    private void authenticate(String email, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
