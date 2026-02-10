package com.example.demo.service;

import com.example.demo.model.JobListing;

import java.util.List;

public interface JobListingService {

    JobListing createJob(JobListing jobListing);

    List<JobListing> getAllJobs();

    List<JobListing> getJobsByEmployerId(Integer employerId);

    List<JobListing> searchJobsByTitle(String keyword);

    JobListing getJobById(Integer jobId);

    JobListing updateJob(Integer jobId, JobListing updatedJob);

    void deleteJob(Integer jobId);
}
