package com.example.authservice.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "organizationAuthorization")
public class OrganizationAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dedicatedPaper;

    @ManyToOne
    @JoinColumn(name = "authorization_id" , referencedColumnName = "id")
    Authorization authorization;

    @ManyToOne
    @JoinColumn(name = "organization_id",referencedColumnName = "id")
    Organization organization;

}
