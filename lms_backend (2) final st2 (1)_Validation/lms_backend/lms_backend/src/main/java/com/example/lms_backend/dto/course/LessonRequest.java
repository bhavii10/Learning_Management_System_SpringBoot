package com.example.lms_backend.dto.course;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonRequest {
    private String title;
    private String content;   // ✅ Add this so frontend can send content
}
