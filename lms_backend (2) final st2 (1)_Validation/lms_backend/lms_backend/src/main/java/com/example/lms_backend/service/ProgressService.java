package com.example.lms_backend.service;

import com.example.lms_backend.model.Progress;
import com.example.lms_backend.model.User;
import com.example.lms_backend.model.Course;
import com.example.lms_backend.repository.ProgressRepository;
import com.example.lms_backend.repository.UserRepository;
import com.example.lms_backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final CourseRepository coursesRepository;

    // Create Progress when new course is started
    public Progress startCourse(Long userId, Long courseId, int totalLessons) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Course course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Progress progress = Progress.builder()
                .user(user)
                .course(course)
                .totalLessons(totalLessons)
                .completedLessons(0)
                .progressPercentage(0.0)
                .status("Not Started")
                .build();

        return progressRepository.save(progress);
    }

    // Update Progress (after completing a lesson)
    public Progress updateProgress(Long progressId, int completedLessons) {
        Progress progress = progressRepository.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Progress not found"));

        progress.setCompletedLessons(completedLessons);

        double percentage = (double) completedLessons / progress.getTotalLessons() * 100;
        progress.setProgressPercentage(percentage);

        if (percentage == 0) progress.setStatus("Not Started");
        else if (percentage < 100) progress.setStatus("In Progress");
        else progress.setStatus("Completed");

        return progressRepository.save(progress);
    }

    // Get all progress for a user
    public List<Progress> getProgressByUser(Long userId) {
        return progressRepository.findByUserId(userId);
    }

    // Delete Progress (reset)
    public void resetProgress(Long progressId) {
        progressRepository.deleteById(progressId);
    }
}