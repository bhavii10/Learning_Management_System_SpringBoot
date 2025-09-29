////package com.example.lms_backend.model;
////
////import jakarta.persistence.*;
////import lombok.*;
////
////@Entity
////@Data
////@NoArgsConstructor
////@AllArgsConstructor
////public class Assessment {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////
////    private String question;
////    private String optionA;
////    private String optionB;
////    private String optionC;
////    private String optionD;
////    private String correctAnswer; // e.g. "A", "B", "C", "D"
////
////    @ManyToOne
////    @JoinColumn(name = "course_id")
////    private Course course;
////}
//
//package com.example.lms_backend.model;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategies;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//public class Assessment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String question;
//    private String optionA;
//    private String optionB;
//    private String optionC;
//    private String optionD;
//    private String correctAnswer;
//
//    @ManyToOne
//    @JoinColumn(name = "course_id")
//    private Course course;
//}
package com.example.lms_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "assessment")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Assessment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = Instant.now(); }
}