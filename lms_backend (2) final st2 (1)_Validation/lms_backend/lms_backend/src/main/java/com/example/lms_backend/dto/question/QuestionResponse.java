package com.example.lms_backend.dto.question;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionResponse {
    private Long id;
    private Long assessmentId;
    private String questionText;
    private List<String> options;
    private String correctAnswer; // admin-only if needed
    private Integer marks;
}