package com.example.demo.service;


import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Resume;

public interface ResumeService {
    Resume uploadResume(MultipartFile file);
    Resume getResumeById(int resumeId);
    Resume updateResume(int resumeId, MultipartFile newFile);

}
