package com.example.authservice.ServiceImpl;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.exception.EmailAlreadyUsedException;
import com.example.authservice.model.Admin;
import com.example.authservice.repository.AdminRepository;
import com.example.authservice.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Admin getAdmin(Long id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new RuntimeException("admin not found"+ id)
        );
    }

    @Override
    public Admin registerAdmin(AdminDto adminDto) {
        adminRepository.findByEmail(adminDto.getEmail()).ifPresent(org -> {
            throw new EmailAlreadyUsedException("Email already in use: " + adminDto.getEmail());
        });

        Admin admin = new Admin();
        admin.setName(adminDto.getName());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        admin.setEmail(adminDto.getEmail());

        System.out.println(admin);
        adminRepository.save(admin);

        return admin;
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void changeAdminPassword(Long id, String newPassword) {
        Admin admin = getAdmin(id);
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);
    }
}
