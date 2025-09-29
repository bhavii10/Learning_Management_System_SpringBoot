package com.example.lms_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessment_id", nullable = false)
    private Long assessmentId;

    @Column(name = "question_text", columnDefinition = "TEXT", nullable = false)
    private String questionText;

    // store JSON string; MySQL JSON or TEXT works
    @Column(columnDefinition = "JSON", nullable = false)
    private String options; // e.g. '["A","B","C","D"]' or if JSON unsupported, a JSON string in TEXT

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Column(nullable = false)
    private Integer marks = 1;
}