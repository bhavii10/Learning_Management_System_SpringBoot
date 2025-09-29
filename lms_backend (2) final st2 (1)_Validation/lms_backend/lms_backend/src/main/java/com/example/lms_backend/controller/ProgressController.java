package com.example.lms_backend.controller;

import com.example.lms_backend.model.Progress;
import com.example.lms_backend.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    // Start a new course
    @PostMapping("/start")
    public ResponseEntity<Progress> startCourse(
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestParam int totalLessons) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(progressService.startCourse(userId, courseId, totalLessons));
    }

    // Update progress
    @PutMapping("/{progressId}")
    public ResponseEntity<Progress> updateProgress(
            @PathVariable Long progressId,
            @RequestParam int completedLessons) {
        return ResponseEntity.ok(progressService.updateProgress(progressId, completedLessons));
    }

    // Get progress for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getProgressByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgressByUser(userId));
    }

    // Reset/Delete progress
    @DeleteMapping("/{progressId}")
    public ResponseEntity<Void> resetProgress(@PathVariable Long progressId) {
        progressService.resetProgress(progressId);
        return ResponseEntity.noContent().build();
    }
}