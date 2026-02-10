package com.example.demo.serviceImpl;

import com.example.demo.model.JobListing;
import com.example.demo.repository.JobListingRepository;
import com.example.demo.service.JobListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobListingServiceImpl implements JobListingService {

	  @Autowired
	    private JobListingRepository jobListingRepository;

	    @Override
	    public JobListing createJob(JobListing jobListing) {
	        return jobListingRepository.save(jobListing);
	    }

	    @Override
	    public List<JobListing> getAllJobs() {
	        return jobListingRepository.findAllWithEmployer();
	    }

	    @Override
	    public List<JobListing> getJobsByEmployerId(Integer employerId) {
	        return jobListingRepository.findByEmployerId(employerId);
	    }

	    @Override
	    public List<JobListing> searchJobsByTitle(String keyword) {
	        return jobListingRepository.searchByJobTitleContaining(keyword);
	    }

	    @Override
	    public JobListing getJobById(Integer jobId) {
	        Optional<JobListing> optional = jobListingRepository.findById(jobId);
	        return optional.orElse(null);
	    }

	    @Override
	    public JobListing updateJob(Integer jobId, JobListing updatedJob) {
	        JobListing existing = getJobById(jobId);
	        if (existing != null) {
	            existing.setJobTitle(updatedJob.getJobTitle());
	            existing.setJobDescription(updatedJob.getJobDescription());
	            existing.setLocation(updatedJob.getLocation());
	            existing.setSalaryRange(updatedJob.getSalaryRange());
	            existing.setSkillsRequired(updatedJob.getSkillsRequired());
	            return jobListingRepository.save(existing);
	        }
	        return null;
	    }

	    @Override
	    public void deleteJob(Integer jobId) {
	        jobListingRepository.deleteById(jobId);
	    }
}
