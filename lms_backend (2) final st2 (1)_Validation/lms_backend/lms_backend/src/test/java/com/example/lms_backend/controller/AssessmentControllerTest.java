package com.example.lms_backend.controller;

import com.example.lms_backend.dto.assessment.AssessmentRequest;
import com.example.lms_backend.dto.assessment.AssessmentResponse;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.exception.GlobalExceptionHandler;
import com.example.lms_backend.service.AssessmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AssessmentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AssessmentController controller;

    @Mock
    private AssessmentService service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // handles Instant serialization

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreateAssessment() throws Exception {
        AssessmentRequest req = new AssessmentRequest(1L, "Test Assessment");
        AssessmentResponse res = AssessmentResponse.builder()
                .id(10L)
                .courseId(1L)
                .title("Test Assessment")
                .createdAt(Instant.now())
                .build();

        when(service.create(any(AssessmentRequest.class))).thenReturn(res);

        mockMvc.perform(post("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("Test Assessment"))
                .andExpect(jsonPath("$.courseId").value(1));
    }

    @Test
    void testListByCourse() throws Exception {
        List<AssessmentResponse> list = Arrays.asList(
                AssessmentResponse.builder().id(1L).courseId(1L).title("A1").createdAt(Instant.now()).build(),
                AssessmentResponse.builder().id(2L).courseId(1L).title("A2").createdAt(Instant.now()).build()
        );

        when(service.listByCourse(1L)).thenReturn(list);

        mockMvc.perform(get("/api/assessments/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A1"))
                .andExpect(jsonPath("$[1].title").value("A2"));
    }

    @Test
    void testGetAssessment() throws Exception {
        AssessmentResponse res = AssessmentResponse.builder()
                .id(1L)
                .courseId(1L)
                .title("A1")
                .createdAt(Instant.now())
                .build();

        when(service.get(1L)).thenReturn(res);

        mockMvc.perform(get("/api/assessments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("A1"));
    }

    @Test
    void testGetAssessmentNotFound() throws Exception {
        when(service.get(999L)).thenThrow(new ApiException("Assessment not found"));

        mockMvc.perform(get("/api/assessments/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Assessment not found"));
    }

    @Test
    void testUpdateAssessment() throws Exception {
        AssessmentRequest req = new AssessmentRequest(1L, "Updated Title");
        AssessmentResponse res = AssessmentResponse.builder()
                .id(1L)
                .courseId(1L)
                .title("Updated Title")
                .createdAt(Instant.now())
                .build();

        when(service.update(eq(1L), any(AssessmentRequest.class))).thenReturn(res);

        mockMvc.perform(put("/api/assessments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testDeleteAssessment() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/assessments/1"))
                .andExpect(status().isNoContent());
    }
}