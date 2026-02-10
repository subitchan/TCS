package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "job_seeker_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerProfile {

    @Id
    @Column(name = "profile_id")
    private Integer profileId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "skills")
    private String skills;

    @Column(name = "education")
    private String education;

 

    
}
