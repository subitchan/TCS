package com.example.demo.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "resume")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resumeId;

    @Lob
    @Column(name = "resume_data", nullable = false)
    private byte[] resumeData;

    @Column(name = "resume_filename", nullable = false)
    private String resumeFilename;
}
