package com.example.demo.controller;



import com.example.demo.model.JobApplication;
import com.example.demo.model.JobSeekerProfile;
import com.example.demo.service.JobApplicationService;
import com.example.demo.service.JobSeekerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/jobSeeker")

public class JobSeekerProfileController {

    @Autowired
    private JobSeekerProfileService jobSeekerService;

    @PostMapping("/addJobSeeker")
    public ResponseEntity<JobSeekerProfile> addJobSeeker(@RequestBody JobSeekerProfile jobSeeker) {
        return ResponseEntity.ok(jobSeekerService.addJobSeeker(jobSeeker));
    }


    @GetMapping("/showJobSeeker")
    public List<JobSeekerProfile> getAllJobSeekers() {
        return jobSeekerService.showAllJobSeekers();
    }
    
    @GetMapping("/searchByJobSeekerName")
    public ResponseEntity<List<JobSeekerProfile>> searchByName(@RequestParam String name) {
        List<JobSeekerProfile> seekers = jobSeekerService.searchByJobSeekerName(name);
        return ResponseEntity.ok(seekers);
    }
    
    @GetMapping("/searchById/{id}")
    public ResponseEntity<JobSeekerProfile> getJobSeekerById(@PathVariable Integer id) {
        JobSeekerProfile profile = jobSeekerService.getJobSeekerById(id);
        return ResponseEntity.ok(profile);
    }
 
    
    @PutMapping("/updateJobSeeker/{id}")
    public ResponseEntity<String> updateJobSeeker(
            @PathVariable Integer id,
            @RequestBody JobSeekerProfile profile) {
        jobSeekerService.updateJobSeeker(id, profile);
        return ResponseEntity.ok("Job Seeker profile updated successfully.");
    }

    @DeleteMapping("/deleteJobSeeker/{id}")
    public ResponseEntity<String> deleteJobSeeker(@PathVariable Integer id) {
        jobSeekerService.deleteJobSeeker(id);
        return ResponseEntity.ok("Job Seeker profile deleted successfully.");
    }
    
    @GetMapping("/JobSeekerIdByName")
    public Integer getJobSeekerIdByName(@RequestParam String name) {
        return jobSeekerService.getJobSeekerIdByName(name);
    }
    


}

