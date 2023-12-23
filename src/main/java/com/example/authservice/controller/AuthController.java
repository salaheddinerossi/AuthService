package com.example.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.authservice.dto.LoginDto;
import com.example.authservice.security.JwtTokenUtil;
import com.example.authservice.response.JwtResponse;
import com.example.authservice.repository.OrganizationRepository;
import com.example.authservice.repository.AdminRepository;

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
    public ResponseEntity<?> createOrganizationAuthenticationToken(@RequestBody LoginDto loginDto) {
        try {
            authenticate(loginDto.getEmail(), loginDto.getPassword());
            if (organizationRepository.findByEmail(loginDto.getEmail()).isPresent()) {
                final String token = jwtTokenUtil.generateToken(loginDto.getEmail(), "ROLE_ORGANIZATION");
                return ResponseEntity.ok(new JwtResponse(token));
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
    public ResponseEntity<?> createAdminAuthenticationToken(@RequestBody LoginDto loginDto) {
        try {
            authenticate(loginDto.getEmail(), loginDto.getPassword());
            if (adminRepository.findByEmail(loginDto.getEmail()).isPresent()) {
                final String token = jwtTokenUtil.generateToken(loginDto.getEmail(), "ROLE_ADMIN");
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                throw new BadCredentialsException("Invalid admin credentials");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            e.printStackTrace();  // It's helpful to log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    private void authenticate(String email, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
