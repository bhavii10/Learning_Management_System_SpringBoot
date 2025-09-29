package com.example.lms_backend.dto.question;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionRequest {
    private Long assessmentId;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private Integer marks;
}