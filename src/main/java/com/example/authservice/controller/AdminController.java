package com.example.authservice.controller;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.model.Admin;
import com.example.authservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public Admin registerAdmin(@RequestBody AdminDto adminDto){
        System.out.println(adminDto);
        return adminService.registerAdmin(adminDto);
    }

    @GetMapping("/{id}")
    public Admin getAdmin(@PathVariable Long id){
        return adminService.getAdmin(id);
    }

    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return "admind deleted";
    }

    @PatchMapping("/changepassword")
    public String changePassword(@RequestBody Long id,String newPassword){
        adminService.changeAdminPassword(id,newPassword);
        return "password updated";
    }
}
