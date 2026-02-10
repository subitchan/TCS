package com.example.demo;

import com.example.demo.controller.JobSeekerProfileController;
import com.example.demo.model.JobSeekerProfile;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.JobSeekerProfileService;
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

@WebMvcTest(JobSeekerProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class JobSeekerProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobSeekerProfileService jobSeekerService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private JobSeekerProfile seeker1;
    private JobSeekerProfile seeker2;

    @BeforeEach
    void setUp() {
        seeker1 = new JobSeekerProfile(1, "John Doe", "1234567890", "john@example.com", "Java, Spring Boot", "B.Tech");
        seeker2 = new JobSeekerProfile(2, "Jane Smith", "9876543210", "jane@example.com", "Angular, TypeScript", "MCA");
    }

    @Test
    void testAddJobSeeker() throws Exception {
        Mockito.when(jobSeekerService.addJobSeeker(Mockito.any(JobSeekerProfile.class))).thenReturn(seeker1);

        mockMvc.perform(post("/jobSeeker/addJobSeeker")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seeker1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    @Test
    void testGetAllJobSeekers() throws Exception {
        List<JobSeekerProfile> seekers = Arrays.asList(seeker1, seeker2);
        Mockito.when(jobSeekerService.showAllJobSeekers()).thenReturn(seekers);

        mockMvc.perform(get("/jobSeeker/showJobSeeker"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testSearchByJobSeekerName() throws Exception {
        List<JobSeekerProfile> seekers = Arrays.asList(seeker1);
        Mockito.when(jobSeekerService.searchByJobSeekerName("John")).thenReturn(seekers);

        mockMvc.perform(get("/jobSeeker/searchByJobSeekerName").param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("John Doe"));
    }

    @Test
    void testGetJobSeekerById() throws Exception {
        Mockito.when(jobSeekerService.getJobSeekerById(1)).thenReturn(seeker1);

        mockMvc.perform(get("/jobSeeker/searchById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testUpdateJobSeeker() throws Exception {
        Mockito.when(jobSeekerService.updateJobSeeker(Mockito.eq(1), Mockito.any(JobSeekerProfile.class)))
                .thenReturn(seeker1);

        mockMvc.perform(put("/jobSeeker/updateJobSeeker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seeker1)))
                .andExpect(status().isOk())
                .andExpect(content().string("Job Seeker profile updated successfully."));
    }

    @Test
    void testDeleteJobSeeker() throws Exception {
        Mockito.doNothing().when(jobSeekerService).deleteJobSeeker(1);

        mockMvc.perform(delete("/jobSeeker/deleteJobSeeker/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Job Seeker profile deleted successfully."));
    }

    @Test
    void testGetJobSeekerIdByName() throws Exception {
        Mockito.when(jobSeekerService.getJobSeekerIdByName("John")).thenReturn(1);

        mockMvc.perform(get("/jobSeeker/JobSeekerIdByName").param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
