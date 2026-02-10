package com.example.demo;

import com.example.demo.controller.JobListingController;
import com.example.demo.model.EmployerProfile;
import com.example.demo.model.JobListing;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.JobListingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobListingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class JobListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobListingService jobListingService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private JobListing job1;
    private JobListing job2;
    private EmployerProfile employer;

    @BeforeEach
    void setUp() {
        employer = new EmployerProfile();
        employer.setEmployerId(1);
        employer.setCompanyName("HexaCorp");

        job1 = new JobListing();
        job1.setJobId(101);
        job1.setJobTitle("Java Developer");
        job1.setJobDescription("Backend Java work");
        job1.setLocation("Bangalore");
        job1.setSalaryRange("8-12 LPA");
        job1.setSkillsRequired("Java, Spring Boot");
        job1.setEmployer(employer);

        job2 = new JobListing();
        job2.setJobId(102);
        job2.setJobTitle("Frontend Developer");
        job2.setJobDescription("Angular and TypeScript work");
        job2.setLocation("Chennai");
        job2.setSalaryRange("6-10 LPA");
        job2.setSkillsRequired("Angular, TypeScript");
        job2.setEmployer(employer);
    }

    @Test
    void testCreateJob() throws Exception {
        Mockito.when(jobListingService.createJob(Mockito.any(JobListing.class)))
                .thenReturn(job1);

        mockMvc.perform(post("/jobListing/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(job1)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.jobTitle").value("Java Developer"));
    }

    @Test
    void testGetAllJobs() throws Exception {
        List<JobListing> jobs = Arrays.asList(job1, job2);
        Mockito.when(jobListingService.getAllJobs()).thenReturn(jobs);

        mockMvc.perform(get("/jobListing/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetJobsByEmployerId() throws Exception {
        List<JobListing> jobs = Arrays.asList(job1);
        Mockito.when(jobListingService.getJobsByEmployerId(1)).thenReturn(jobs);

        mockMvc.perform(get("/jobListing/byEmployer/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testSearchJobs() throws Exception {
        List<JobListing> jobs = Arrays.asList(job1);
        Mockito.when(jobListingService.searchJobsByTitle("Java")).thenReturn(jobs);

        mockMvc.perform(get("/jobListing/search").param("keyword", "Java"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetJobById() throws Exception {
        Mockito.when(jobListingService.getJobById(101)).thenReturn(job1);

        mockMvc.perform(get("/jobListing/101"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.jobTitle").value("Java Developer"));
    }

    @Test
    void testUpdateJob() throws Exception {
        Mockito.when(jobListingService.updateJob(Mockito.eq(101), Mockito.any(JobListing.class)))
                .thenReturn(job1);

        mockMvc.perform(put("/jobListing/update/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(job1)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.jobTitle").value("Java Developer"));
    }

    @Test
    void testDeleteJob() throws Exception {
        Mockito.doNothing().when(jobListingService).deleteJob(101);

        mockMvc.perform(delete("/jobListing/delete/101"))
               .andExpect(status().isNoContent());
    }
}
