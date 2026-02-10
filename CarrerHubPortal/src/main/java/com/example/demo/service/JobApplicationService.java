package com.example.demo.service;

import java.util.List;

import com.example.demo.model.JobApplication;
import com.example.demo.model.JobApplication.ApplicationStatus;

public interface JobApplicationService {
    JobApplication applyForJob(int jobId, int profileId, int resumeId);
    boolean hasAlreadyApplied(Integer jobId, Integer profileId);
    List<JobApplication> getApplicationsByProfileId(Integer profileId);
    List<JobApplication> getApplicationsByEmployerId(Integer employerId);
    
    JobApplication updateApplicationStatus(Integer applicationId, ApplicationStatus status);

    
}
