package com.example.lms_backend.dto.assessment;

import lombok.*;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssessmentResponse {
    private Long id;
    private Long courseId;
    private String title;
    private Instant createdAt;
}