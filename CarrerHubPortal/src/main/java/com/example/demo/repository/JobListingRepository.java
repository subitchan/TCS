package com.example.demo.repository;

import com.example.demo.model.JobListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobListingRepository extends JpaRepository<JobListing, Integer> {

    @Query("SELECT j FROM JobListing j JOIN FETCH j.employer")
    List<JobListing> findAllWithEmployer();

    @Query("SELECT j FROM JobListing j JOIN FETCH j.employer WHERE j.employer.employerId = :employerId")
    List<JobListing> findByEmployerId(@Param("employerId") Integer employerId);

    @Query("SELECT j FROM JobListing j JOIN FETCH j.employer WHERE LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<JobListing> searchByJobTitleContaining(@Param("keyword") String keyword);
}
