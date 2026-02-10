package com.example.demo.repository;

import com.example.demo.model.JobSeekerProfile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {
   
	List<JobSeekerProfile> findByFullNameContainingIgnoreCase(String fullName);
	
	JobSeekerProfile findByFullName(String fullName);
	
}
