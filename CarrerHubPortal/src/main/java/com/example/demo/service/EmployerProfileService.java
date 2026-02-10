package com.example.demo.service;

import com.example.demo.model.EmployerProfile;
import java.util.List;

public interface EmployerProfileService {
    EmployerProfile addEmployer(EmployerProfile employer) ;
    EmployerProfile getEmployerById(Integer employerId);
    List<EmployerProfile> getAllEmployers();
    
    String updateEmployerProfile(Integer employerId, EmployerProfile updatedProfile);

}
