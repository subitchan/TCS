package com.example.demo;

import com.example.demo.controller.EmployerProfileController;
import com.example.demo.model.EmployerProfile;
import com.example.demo.service.EmployerProfileService;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployerProfileController.class)
@AutoConfigureMockMvc(addFilters = false) 
public class EmployerProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployerProfileService employerService;

    // ✅ Needed only if security config references these beans
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployerProfile employer1;
    private EmployerProfile employer2;

    @BeforeEach
    void setUp() {
        employer1 = new EmployerProfile(1, "TechCorp", "https://techcorp.com", "hr@techcorp.com");
        employer2 = new EmployerProfile(2, "InnovateX", "https://innovatex.io", "contact@innovatex.io");
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testAddEmployer() throws Exception {
        Mockito.when(employerService.addEmployer(Mockito.any(EmployerProfile.class)))
               .thenReturn(employer1);

        mockMvc.perform(post("/employer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employer1)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.companyName").value("TechCorp"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testGetAllEmployers() throws Exception {
        List<EmployerProfile> employers = Arrays.asList(employer1, employer2);
        Mockito.when(employerService.getAllEmployers()).thenReturn(employers);

        mockMvc.perform(get("/employer/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[1].companyName").value("InnovateX"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testGetEmployerById_Found() throws Exception {
        Mockito.when(employerService.getEmployerById(1)).thenReturn(employer1);

        mockMvc.perform(get("/employer/get/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.companyName").value("TechCorp"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testGetEmployerById_NotFound() throws Exception {
        Mockito.when(employerService.getEmployerById(99)).thenReturn(null);

        mockMvc.perform(get("/employer/get/99"))
               .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "EMPLOYER")
    void testUpdateEmployer() throws Exception {
        Mockito.when(employerService.updateEmployerProfile(Mockito.eq(1), Mockito.any(EmployerProfile.class)))
               .thenReturn("Employer updated successfully");

        mockMvc.perform(put("/employer/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employer1)))
               .andExpect(status().isOk())
               .andExpect(content().string("Employer updated successfully"));
    }
}
