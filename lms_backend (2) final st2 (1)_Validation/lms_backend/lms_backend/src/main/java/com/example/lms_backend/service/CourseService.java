package com.example.lms_backend.service;

import com.example.lms_backend.dto.course.*;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.model.Course;
import com.example.lms_backend.model.Lesson;
import com.example.lms_backend.repository.CourseRepository;
import com.example.lms_backend.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public CourseResponse create(CourseRequest req) {
        if (courseRepository.existsByTitleIgnoreCase(req.getTitle())) {
            throw new ApiException("Course with this title already exists");
        }

        Course c = Course.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .instructor(req.getInstructor())
                .build();

        if (req.getLessons() != null) {
            List<Lesson> lessons = req.getLessons().stream()
                    .map(l -> Lesson.builder()
                            .title(l.getTitle())
                            .content(l.getContent())
                            .completed(false)
                            .course(c)
                            .build())
                    .toList();
            c.setLessons(lessons);
        }

        // 🔹 Cascade saves lessons together with course
        courseRepository.save(c);

        // 🔹 Refetch with lessons
        Course savedCourse = courseRepository.findByIdWithLessons(c.getId())
                .orElseThrow(() -> new ApiException("Could not reload course"));

        return toResp(savedCourse);
    }

    public List<CourseResponse> findAll() {
        return courseRepository.findAll().stream().map(this::toResp).toList();
    }

    public CourseResponse findById(Long id) {
        Course c = courseRepository.findByIdWithLessons(id)
                .orElseThrow(() -> new ApiException("Course not found"));
        return toResp(c);
    }

    public CourseResponse update(Long id, CourseRequest req) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new ApiException("Course not found"));

        c.setTitle(req.getTitle());
        c.setDescription(req.getDescription());
        c.setInstructor(req.getInstructor());
        courseRepository.save(c);

        if (req.getLessons() != null) {
            List<Lesson> lessons = req.getLessons().stream()
                    .map(l -> Lesson.builder()
                            .title(l.getTitle())
                            .content(l.getContent()) // ✅ added
                            .completed(false)
                            .course(c)
                            .build())
                    .toList();
            lessonRepository.saveAll(lessons);
            c.setLessons(lessons);
        }

        return toResp(c);
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) throw new ApiException("Course not found");
        courseRepository.deleteById(id);
    }

    private CourseResponse toResp(Course c) {
        List<LessonResponse> lessonResponses = null;

        if (c.getLessons() != null) {
            lessonResponses = c.getLessons().stream()
                    .map(l -> LessonResponse.builder()
                            .id(l.getId())
                            .title(l.getTitle())
                            .content(l.getContent())   // ✅ added
                            .completed(l.isCompleted())
                            .build())
                    .collect(Collectors.toList());
        }

        int progress = 0;
        if (lessonResponses != null && !lessonResponses.isEmpty()) {
            long completed = lessonResponses.stream().filter(LessonResponse::isCompleted).count();
            progress = (int) ((completed * 100.0) / lessonResponses.size());
        }

        return CourseResponse.builder()
                .id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .instructor(c.getInstructor())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .lessons(lessonResponses)
                .progress(progress)
                .build();
    }
}
