package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JobApplication;
import com.example.demo.model.JobApplication.ApplicationStatus;
import com.example.demo.service.JobApplicationService;

@RestController
@RequestMapping("/jobApplication")
public class JobApplicationController {
	
	@Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping("/apply")
    public JobApplication applyToJob(@RequestParam int jobId,
                                     @RequestParam int profileId,
                                     @RequestParam int resumeId) {
        return jobApplicationService.applyForJob(jobId, profileId, resumeId);
    }
	
	@GetMapping("/check")
	public ResponseEntity<Boolean> checkIfApplied(@RequestParam int jobId, @RequestParam int profileId) {
	    boolean exists = jobApplicationService.hasAlreadyApplied(jobId, profileId);
	    return ResponseEntity.ok(exists);
	}
	
	@GetMapping("/byProfile/{profileId}")
	public ResponseEntity<List<JobApplication>> getApplicationsByProfile(@PathVariable Integer profileId) {
	    List<JobApplication> applications = jobApplicationService.getApplicationsByProfileId(profileId);
	    return ResponseEntity.ok(applications);
	}
	
	@GetMapping("/byEmployer/{employerId}")
	public ResponseEntity<List<JobApplication>> getApplicationsByEmployerId(@PathVariable Integer employerId) {
	    List<JobApplication> applications = jobApplicationService.getApplicationsByEmployerId(employerId);
	    return ResponseEntity.ok(applications);
	}
	
	@PutMapping("/status/{applicationId}")
	public ResponseEntity<String> updateStatus(
	        @PathVariable Integer applicationId,
	        @RequestParam ApplicationStatus status) {
	    
		jobApplicationService.updateApplicationStatus(applicationId, status);
	    return ResponseEntity.ok("Application status updated successfully.");
	}

}
