package com.example.demo;

import com.example.demo.controller.JobApplicationController;
import com.example.demo.model.JobApplication;
import com.example.demo.model.JobApplication.ApplicationStatus;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.JobApplicationService;

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

@WebMvcTest(JobApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class JobApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobApplicationService jobApplicationService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    private JobApplication application1;
    private JobApplication application2;

    @BeforeEach
    void setUp() {
        application1 = new JobApplication();
        application1.setApplicationId(1);
        application1.setApplicationStatus(ApplicationStatus.APPLIED);

        application2 = new JobApplication();
        application2.setApplicationId(2);
        application2.setApplicationStatus(ApplicationStatus.SHORTLISTED);
    }

    @Test
    void testApplyToJob() throws Exception {
        Mockito.when(jobApplicationService.applyForJob(101, 201, 301)).thenReturn(application1);

        mockMvc.perform(post("/jobApplication/apply")
                .param("jobId", "101")
                .param("profileId", "201")
                .param("resumeId", "301"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.applicationId").value(1))
               .andExpect(jsonPath("$.applicationStatus").value("APPLIED"));
    }

    @Test
    void testCheckIfApplied() throws Exception {
        Mockito.when(jobApplicationService.hasAlreadyApplied(101, 201)).thenReturn(true);

        mockMvc.perform(get("/jobApplication/check")
                .param("jobId", "101")
                .param("profileId", "201"))
               .andExpect(status().isOk())
               .andExpect(content().string("true"));
    }

    @Test
    void testGetApplicationsByProfile() throws Exception {
        List<JobApplication> apps = Arrays.asList(application1, application2);
        Mockito.when(jobApplicationService.getApplicationsByProfileId(201)).thenReturn(apps);

        mockMvc.perform(get("/jobApplication/byProfile/201"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetApplicationsByEmployer() throws Exception {
        List<JobApplication> apps = Arrays.asList(application1);
        Mockito.when(jobApplicationService.getApplicationsByEmployerId(501)).thenReturn(apps);

        mockMvc.perform(get("/jobApplication/byEmployer/501"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testUpdateStatus() throws Exception {
      
        Mockito.when(jobApplicationService.updateApplicationStatus(1, ApplicationStatus.REJECTED))
               .thenReturn(application1); // Return dummy JobApplication

        mockMvc.perform(put("/jobApplication/status/1")
                .param("status", "REJECTED"))
               .andExpect(status().isOk())
               .andExpect(content().string("Application status updated successfully."));
    }
}
