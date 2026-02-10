package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Resume;
import com.example.demo.service.ResumeService;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public Resume uploadResume(@RequestParam("file") MultipartFile file) {
        return resumeService.uploadResume(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable int id) {
        Resume resume = resumeService.getResumeById(id);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + resume.getResumeFilename())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resume.getResumeData());
    }
    
    @PutMapping("/update/{resumeId}")
    public Resume updateResume(@PathVariable int resumeId,
                               @RequestParam("file") MultipartFile newFile) {
        return resumeService.updateResume(resumeId, newFile);
    }
}

