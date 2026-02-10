package com.example.demo.controller;

import com.example.demo.model.EmployerProfile;
import com.example.demo.service.EmployerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer")
public class EmployerProfileController {

    @Autowired
    private EmployerProfileService employerService;

    //  Add Employer
    @PostMapping("/add")
    public ResponseEntity<EmployerProfile> addEmployer(@RequestBody EmployerProfile employer) {
        return ResponseEntity.ok(employerService.addEmployer(employer));
    }

    //  Show All Employers
    @GetMapping("/all")
    public ResponseEntity<List<EmployerProfile>> getAllEmployers() {
        return ResponseEntity.ok(employerService.getAllEmployers());
    }

    //  Search Employer by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<EmployerProfile> getEmployerById(@PathVariable("id") Integer id) {
        EmployerProfile employer = employerService.getEmployerById(id);
        if (employer != null) {
            return ResponseEntity.ok(employer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
//  Update Employer by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployer(
            @PathVariable("id") Integer employerId,
            @RequestBody EmployerProfile updatedProfile) {

        String result = employerService.updateEmployerProfile(employerId, updatedProfile);
        return ResponseEntity.ok(result);
    }
}
