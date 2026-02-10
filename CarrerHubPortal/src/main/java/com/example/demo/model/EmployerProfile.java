package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Table(name = "employer_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerProfile {

    @Id
    @Column(name = "employer_id")
    private Integer employerId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_website")
    private String companyWebsite;

    @Column(name = "contact_email")
    private String contactEmail;
}