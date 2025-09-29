package com.example.lms_backend.controller;

import com.example.lms_backend.model.Course;
import com.example.lms_backend.model.Progress;
import com.example.lms_backend.model.User;
import com.example.lms_backend.service.ProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressControllerTest {

    @Mock
    private ProgressService progressService;

    @InjectMocks
    private ProgressController progressController;

    private User mockUser;
    private Course mockCourse;
    private Progress mockProgress;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = User.builder().id(1L).name("John").build();
        mockCourse = Course.builder().id(1L).title("Java Basics").build();
        mockProgress = Progress.builder()
                .id(1L)
                .user(mockUser)
                .course(mockCourse)
                .completedLessons(0)
                .totalLessons(10)
                .progressPercentage(0.0)
                .status("Not Started")
                .build();
    }

    @Test
    void testStartCourse() {
        when(progressService.startCourse(1L, 1L, 10)).thenReturn(mockProgress);

        ResponseEntity<Progress> response = progressController.startCourse(1L, 1L, 10);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockProgress, response.getBody());
        verify(progressService).startCourse(1L, 1L, 10);
    }

    @Test
    void testUpdateProgress() {
        mockProgress.setCompletedLessons(5);
        mockProgress.setProgressPercentage(50.0);
        mockProgress.setStatus("In Progress");

        when(progressService.updateProgress(1L, 5)).thenReturn(mockProgress);

        ResponseEntity<Progress> response = progressController.updateProgress(1L, 5);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProgress, response.getBody());
        verify(progressService).updateProgress(1L, 5);
    }

    @Test
    void testGetProgressByUser() {
        when(progressService.getProgressByUser(1L)).thenReturn(List.of(mockProgress));

        ResponseEntity<List<Progress>> response = progressController.getProgressByUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(mockProgress, response.getBody().get(0));
        verify(progressService).getProgressByUser(1L);
    }

    @Test
    void testResetProgress() {
        doNothing().when(progressService).resetProgress(1L);

        ResponseEntity<Void> response = progressController.resetProgress(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(progressService).resetProgress(1L);
    }
}