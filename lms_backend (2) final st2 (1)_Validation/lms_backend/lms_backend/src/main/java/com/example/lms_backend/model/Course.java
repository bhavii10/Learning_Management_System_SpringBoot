//package com.example.lms_backend.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//
//import java.time.Instant;
//
//@Entity
//@Table(name = "courses")
//@Getter @Setter
//@NoArgsConstructor @AllArgsConstructor @Builder
//public class Course {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank @Column(nullable = false)
//    private String title;
//
//    @Column(length = 2000)
//    private String description;
//
//    @NotBlank @Column(nullable = false)
//    private String instructor; // email or name
//
//    @Column(nullable = false, updatable = false)
//    private Instant createdAt;
//
//    private Instant updatedAt;
//
//    @PrePersist
//    void onCreate() { this.createdAt = Instant.now(); }
//
//    @PreUpdate
//    void onUpdate() { this.updatedAt = Instant.now(); }
//}

package com.example.lms_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String instructor; // email or name

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    // 🔹 New: Lessons list
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
    private int progress;
}
