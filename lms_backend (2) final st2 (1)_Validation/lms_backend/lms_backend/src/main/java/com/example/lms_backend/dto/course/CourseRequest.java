//package com.example.lms_backend.dto.course;
//
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//
//@Getter @Setter
//@NoArgsConstructor @AllArgsConstructor @Builder
//public class CourseRequest {
//    @NotBlank
//    private String title;
//
//    private String description;
//
//    @NotBlank
//    private String instructor;
//
//
//}


package com.example.lms_backend.dto.course;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CourseRequest {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String instructor;

    private List<LessonRequest> lessons;
}
