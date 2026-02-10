package com.example.demo.controller;

import com.example.demo.model.JobListing;
import com.example.demo.service.JobListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobListing")

public class JobListingController {

	 @Autowired
	    private JobListingService jobListingService;

	    @PostMapping("/create")
	    public ResponseEntity<JobListing> createJob(@RequestBody JobListing jobListing) {
	        return ResponseEntity.ok(jobListingService.createJob(jobListing));
	    }

	    @GetMapping("/all")
	    public ResponseEntity<List<JobListing>> getAllJobs() {
	        return ResponseEntity.ok(jobListingService.getAllJobs());
	    }

	    @GetMapping("/byEmployer/{employerId}")
	    public ResponseEntity<List<JobListing>> getJobsByEmployer(@PathVariable Integer employerId) {
	        return ResponseEntity.ok(jobListingService.getJobsByEmployerId(employerId));
	    }

	    @GetMapping("/search")
	    public ResponseEntity<List<JobListing>> searchJobs(@RequestParam String keyword) {
	        return ResponseEntity.ok(jobListingService.searchJobsByTitle(keyword));
	    }

	    @GetMapping("/{jobId}")
	    public ResponseEntity<JobListing> getJobById(@PathVariable Integer jobId) {
	        return ResponseEntity.ok(jobListingService.getJobById(jobId));
	    }

	    @PutMapping("/update/{jobId}")
	    public ResponseEntity<JobListing> updateJob(@PathVariable Integer jobId, @RequestBody JobListing updatedJob) {
	        return ResponseEntity.ok(jobListingService.updateJob(jobId, updatedJob));
	    }

	    @DeleteMapping("/delete/{jobId}")
	    public ResponseEntity<Void> deleteJob(@PathVariable Integer jobId) {
	        jobListingService.deleteJob(jobId);
	        return ResponseEntity.noContent().build();
	    }
}
