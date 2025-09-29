package com.example.lms_backend.controller;

import com.example.lms_backend.model.Course;
import com.example.lms_backend.model.Lesson;
import com.example.lms_backend.repository.CourseRepository;
import com.example.lms_backend.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonControllerTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonController lessonController;

    private Lesson lesson1;
    private Lesson lesson2;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course = Course.builder()
                .id(1L)
                .title("Sample Course")
                .lessons(new ArrayList<>())
                .progress(0)
                .build();

        lesson1 = Lesson.builder()
                .id(1L)
                .title("Lesson 1")
                .completed(false)
                .course(course)
                .build();

        lesson2 = Lesson.builder()
                .id(2L)
                .title("Lesson 2")
                .completed(false)
                .course(course)
                .build();

        course.getLessons().add(lesson1);
        course.getLessons().add(lesson2);
    }

    @Test
    void testToggleLesson_SuccessFullCompletion() {
        Map<String, Boolean> body = Map.of("completed", true);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson1));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson1);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        ResponseEntity<Map<String, Object>> response = lessonController.toggleLesson(1L, body);

        assertAll(
                () -> assertEquals(200, response.getStatusCodeValue()),
                () -> assertTrue((Boolean) response.getBody().get("completed")),
                () -> assertEquals(50, response.getBody().get("courseProgress")) // 1 of 2 lessons completed = 50%
        );

        verify(lessonRepository, times(1)).findById(1L);
        verify(lessonRepository, times(1)).save(lesson1);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testToggleLesson_PartialCompletion() {
        // mark one lesson already completed
        lesson1.setCompleted(true);

        Map<String, Boolean> body = Map.of("completed", true);

        when(lessonRepository.findById(2L)).thenReturn(Optional.of(lesson2));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson2);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        ResponseEntity<Map<String, Object>> response = lessonController.toggleLesson(2L, body);

        assertAll(
                () -> assertEquals(200, response.getStatusCodeValue()),
                () -> assertTrue((Boolean) response.getBody().get("completed")),
                () -> assertEquals(100, response.getBody().get("courseProgress")) // both completed now
        );

        verify(lessonRepository, times(1)).findById(2L);
        verify(lessonRepository, times(1)).save(lesson2);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testToggleLesson_LessonNotFound() {
        when(lessonRepository.findById(99L)).thenReturn(Optional.empty());

        Map<String, Boolean> body = Map.of("completed", true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> lessonController.toggleLesson(99L, body));

        assertEquals("404 NOT_FOUND \"Lesson not found\"", exception.getMessage());

        verify(lessonRepository, times(1)).findById(99L);
        verify(lessonRepository, never()).save(any());
        verify(courseRepository, never()).save(any());
    }
}