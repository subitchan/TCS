package com.example.demo.serviceImpl;

import com.example.demo.model.EmployerProfile;
import com.example.demo.repository.EmployerProfileRepository;
import com.example.demo.service.EmployerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerProfileServiceImpl implements EmployerProfileService {

    @Autowired
    private EmployerProfileRepository employerRepo;

    @Override
    public EmployerProfile addEmployer(EmployerProfile employer) {
        return employerRepo.save(employer);
    }

    @Override
    public EmployerProfile getEmployerById(Integer employerId) {
        return employerRepo.findById(employerId).orElse(null);
    }

    @Override
    public List<EmployerProfile> getAllEmployers() {
        return employerRepo.findAll();
    }
    
    @Override
    public String updateEmployerProfile(Integer employerId, EmployerProfile updatedProfile) {
        EmployerProfile existing = employerRepo.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer profile not found with ID: " + employerId));

        existing.setCompanyName(updatedProfile.getCompanyName());
        existing.setCompanyWebsite(updatedProfile.getCompanyWebsite());
        existing.setContactEmail(updatedProfile.getContactEmail());

        employerRepo.save(existing);
        return "Employer profile updated successfully.";
    }
}
