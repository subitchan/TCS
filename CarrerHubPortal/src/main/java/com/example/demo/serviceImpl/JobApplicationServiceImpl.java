package com.example.demo.serviceImpl;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.JobApplication;
import com.example.demo.model.JobApplication.ApplicationStatus;
import com.example.demo.model.JobListing;
import com.example.demo.model.JobSeekerProfile;
import com.example.demo.model.Resume;
import com.example.demo.repository.JobApplicationRepository;
import com.example.demo.repository.JobListingRepository;
import com.example.demo.repository.JobSeekerProfileRepository;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.service.JobApplicationService;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobListingRepository jobListingRepository;

    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Override
    public JobApplication applyForJob(int jobId, int profileId, int resumeId) {
        JobListing job = jobListingRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        JobSeekerProfile seeker = jobSeekerProfileRepository.findById(profileId)
            .orElseThrow(() -> new RuntimeException("Profile not found"));

        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new RuntimeException("Resume not found"));

        JobApplication application = JobApplication.builder()
            .job(job)
            .jobSeeker(seeker)
            .resume(resume)
            .applicationStatus(JobApplication.ApplicationStatus.APPLIED)
            .build();

        return jobApplicationRepository.save(application);
    }
    
    @Override
    public boolean hasAlreadyApplied(Integer jobId, Integer profileId) {
        return jobApplicationRepository.existsByJob_JobIdAndJobSeeker_ProfileId(jobId, profileId);
    }
    
    @Override
    public List<JobApplication> getApplicationsByProfileId(Integer profileId) {
        return jobApplicationRepository.findWithDetailsByProfileId(profileId);
    }
    
    public List<JobApplication> getApplicationsByEmployerId(Integer employerId) {
        return jobApplicationRepository.findByEmployerId(employerId);
    }
    
    @Override
    public JobApplication updateApplicationStatus(Integer applicationId, ApplicationStatus status) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setApplicationStatus(status);
        return jobApplicationRepository.save(application);
    }

}
