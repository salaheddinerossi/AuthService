package com.example.authservice.util;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SecurityUtils {
    public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
    public static boolean isOrganization(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ORGANIZATION"));
    }

}
