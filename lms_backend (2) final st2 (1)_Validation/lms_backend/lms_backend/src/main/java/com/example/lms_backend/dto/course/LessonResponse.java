package com.example.lms_backend.dto.course;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResponse {
    private Long id;
    private String title;
    private String content;   // ✅ Add this
    private boolean completed;
}
