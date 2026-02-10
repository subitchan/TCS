package com.example.demo.serviceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Resume;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.service.ResumeService;

import java.io.IOException;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Override
    public Resume uploadResume(MultipartFile file) {
        try {
          
            
            Resume resume=    Resume.builder()
            .resumeData(file.getBytes())
            .resumeFilename(file.getOriginalFilename())
            .build();
            return resumeRepository.save(resume);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading resume", e);
        }
    }

    @Override
    public Resume getResumeById(int resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found with ID: " + resumeId));
    }
    
    @Override
    public Resume updateResume(int resumeId, MultipartFile newFile) {
        Resume existingResume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new RuntimeException("Resume not found with ID: " + resumeId));
        
        try {
            existingResume.setResumeData(newFile.getBytes());
            existingResume.setResumeFilename(newFile.getOriginalFilename());
            return resumeRepository.save(existingResume);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update resume", e);
        }
    }
}
