package com.example.authservice.response;

public class JwtResponse {
    private final String token;

    private Long id;

    private String name;

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JwtResponse(String token,Long id,String name,String role) {
        this.id=id;
        this.name=name;
        this.token = token;
        this.role=role;
    }



    public String getToken() {
        return token;
    }
}
