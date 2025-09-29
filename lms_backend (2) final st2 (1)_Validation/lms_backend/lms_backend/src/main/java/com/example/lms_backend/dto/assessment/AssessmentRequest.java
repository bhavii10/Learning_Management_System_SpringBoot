package com.example.lms_backend.dto.assessment;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssessmentRequest {
    private Long courseId;
    private String title;
}