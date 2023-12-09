package com.example.authservice.service;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.model.Admin;

public interface AdminService {

    Admin getAdmin(Long id);

    Admin registerAdmin(AdminDto adminDto);

    void deleteAdmin(Long id);

    void changeAdminPassword(Long id,String newPassword);


}
