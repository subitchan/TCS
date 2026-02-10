package com.example.demo;

import com.example.demo.controller.ResumeController;
import com.example.demo.model.Resume;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.ResumeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResumeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ResumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResumeService resumeService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private Resume mockResume;
    private byte[] mockBytes;
    private MockMultipartFile mockFile;

    @BeforeEach
    void setUp() {
        mockBytes = "Sample Resume Content".getBytes();
        mockFile = new MockMultipartFile("file", "resume.pdf", "application/pdf", mockBytes);
        mockResume = Resume.builder()
                .resumeId(1)
                .resumeFilename("resume.pdf")
                .resumeData(mockBytes)
                .build();
    }

    @Test
    void testUploadResume() throws Exception {
        Mockito.when(resumeService.uploadResume(Mockito.any())).thenReturn(mockResume);

        mockMvc.perform(multipart("/resume/upload").file(mockFile))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.resumeId").value(1))
               .andExpect(jsonPath("$.resumeFilename").value("resume.pdf"));
    }

    @Test
    void testDownloadResume() throws Exception {
        Mockito.when(resumeService.getResumeById(1)).thenReturn(mockResume);

        mockMvc.perform(get("/resume/1"))
               .andExpect(status().isOk())
               .andExpect(header().string("Content-Disposition", "attachment; filename=resume.pdf"))
               .andExpect(content().bytes(mockBytes));
    }

    @Test
    void testUpdateResume() throws Exception {
        Mockito.when(resumeService.updateResume(Mockito.eq(1), Mockito.any())).thenReturn(mockResume);

        mockMvc.perform(multipart("/resume/update/1").file(mockFile).with(request -> {
            request.setMethod("PUT");
            return request;
        }))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.resumeId").value(1))
        .andExpect(jsonPath("$.resumeFilename").value("resume.pdf"));
    }
}
