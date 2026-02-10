package com.example.demo.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
	boolean existsByJob_JobIdAndJobSeeker_ProfileId(Integer jobId, Integer profileId);
	@Query("SELECT ja FROM JobApplication ja " +
		       "JOIN FETCH ja.job " +
		       "JOIN FETCH ja.jobSeeker " +
		       "JOIN FETCH ja.resume " +
		       "WHERE ja.jobSeeker.profileId = :profileId")
		List<JobApplication> findWithDetailsByProfileId(@Param("profileId") Integer profileId);
	
	@Query("SELECT ja FROM JobApplication ja JOIN FETCH ja.job j WHERE j.employer.employerId = :employerId")
	List<JobApplication> findByEmployerId(@Param("employerId") Integer employerId);
}

