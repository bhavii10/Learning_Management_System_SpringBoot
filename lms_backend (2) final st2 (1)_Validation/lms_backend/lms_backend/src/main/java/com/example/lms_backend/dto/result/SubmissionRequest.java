package com.example.lms_backend.dto.result;

import lombok.*;

import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubmissionRequest {
    private Long assessmentId;
    private Long userId;
    // map questionId -> chosenOption (string)
    private Map<Long, String> answers;
}