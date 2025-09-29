package com.example.lms_backend.dto.result;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubmissionResponse {
    private Integer score;
    private Integer totalMarks;
    private Long resultId; // id of stored assessment_result
}