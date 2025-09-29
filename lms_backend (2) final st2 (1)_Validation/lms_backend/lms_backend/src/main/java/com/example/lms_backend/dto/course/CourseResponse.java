//package com.example.lms_backend.dto.course;
//
//import lombok.*;
//
//import java.time.Instant;
//
//@Getter @Setter
//@NoArgsConstructor @AllArgsConstructor @Builder
//public class CourseResponse {
//    private Long id;
//    private String title;
//    private String description;
//    private String instructor;
//    private Instant createdAt;
//    private Instant updatedAt;
//}




package com.example.lms_backend.dto.course;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String instructor;
    private Instant createdAt;
    private Instant updatedAt;

    private List<LessonResponse> lessons;
    private int progress;
}
