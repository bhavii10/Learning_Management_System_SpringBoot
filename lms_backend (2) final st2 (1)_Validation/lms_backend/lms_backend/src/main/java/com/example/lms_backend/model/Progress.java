package com.example.lms_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Assuming your entity is "Users"

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // Assuming your entity is "Courses"

    private int completedLessons;
    private int totalLessons;

    private double progressPercentage;

    private String status; // e.g. "Not Started", "In Progress", "Completed"
}