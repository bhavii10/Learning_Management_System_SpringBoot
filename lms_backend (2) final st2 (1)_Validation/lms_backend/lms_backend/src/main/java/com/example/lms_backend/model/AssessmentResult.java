package com.example.lms_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "assessment_result")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssessmentResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessment_id", nullable = false)
    private Long assessmentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer score;

    @Column(name = "total_marks", nullable = false)
    private Integer totalMarks;

    @Column(name = "taken_at", nullable = false, updatable = false)
    private Instant takenAt;

    @PrePersist
    public void prePersist() { this.takenAt = Instant.now(); }
}