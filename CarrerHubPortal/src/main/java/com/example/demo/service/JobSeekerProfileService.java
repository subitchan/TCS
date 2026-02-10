package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.JobSeekerProfile;

public interface JobSeekerProfileService {
	JobSeekerProfile addJobSeeker(JobSeekerProfile jobSeeker);
    List<JobSeekerProfile> searchByJobSeekerName(String name);
    List<JobSeekerProfile> showAllJobSeekers();
    
    JobSeekerProfile getJobSeekerById(Integer id);
    JobSeekerProfile updateJobSeeker(Integer id, JobSeekerProfile jobSeeker);
    void deleteJobSeeker(Integer id);
    
   Integer getJobSeekerIdByName(String name);
   

}
