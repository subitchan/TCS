package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.JobSeekerProfile;
import com.example.demo.repository.JobSeekerProfileRepository;
import com.example.demo.service.JobSeekerProfileService;

@Service
public class JobSeekerProfileServiceImpl implements JobSeekerProfileService {

    @Autowired
    private JobSeekerProfileRepository jobSeekerRepo;

    @Override
    public JobSeekerProfile addJobSeeker(JobSeekerProfile jobSeeker) {
        return jobSeekerRepo.save(jobSeeker);
    }

    @Override
    public List<JobSeekerProfile> searchByJobSeekerName(String name) {
        return jobSeekerRepo.findByFullNameContainingIgnoreCase(name);
    }

    @Override
    public List<JobSeekerProfile> showAllJobSeekers() {
        return jobSeekerRepo.findAll();
    }

    @Override
    public JobSeekerProfile getJobSeekerById(Integer id) {
        return jobSeekerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found with ID: " + id));
    }

    @Override
    public JobSeekerProfile updateJobSeeker(Integer id, JobSeekerProfile updatedProfile) {
        JobSeekerProfile existing = jobSeekerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update. Job Seeker not found with ID: " + id));
        
        existing.setFullName(updatedProfile.getFullName());
        existing.setSkills(updatedProfile.getSkills());
        existing.setEducation(updatedProfile.getEducation());
        existing.setPhone(updatedProfile.getPhone());
        existing.setEmail(updatedProfile.getEmail());

        return jobSeekerRepo.save(existing);
    }

    @Override
    public void deleteJobSeeker(Integer id) {
        JobSeekerProfile existing = jobSeekerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot delete. Job Seeker not found with ID: " + id));

        jobSeekerRepo.delete(existing);
    }
    
    @Override
    public Integer getJobSeekerIdByName(String name) {
        JobSeekerProfile profile = jobSeekerRepo.findByFullName(name);
        if (profile != null) {
            return profile.getProfileId();
        } else {
            throw new RuntimeException("Job seeker not found with name: " + name);
        }
    }
}
