package com.example.lms_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String courseTitle;

    private LocalDateTime issuedAt;

    @Lob
    private byte[] pdfData; // PDF ko DB me store karne ke liye
}
