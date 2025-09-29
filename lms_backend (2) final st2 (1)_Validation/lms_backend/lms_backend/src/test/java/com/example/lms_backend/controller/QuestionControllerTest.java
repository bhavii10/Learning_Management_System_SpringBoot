package com.example.lms_backend.controller;

import com.example.lms_backend.dto.question.QuestionRequest;
import com.example.lms_backend.dto.question.QuestionResponse;
import com.example.lms_backend.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createQuestion_returnsCreated() throws Exception {
        QuestionRequest req = QuestionRequest.builder()
                .assessmentId(1L)
                .questionText("Sample Question")
                .options(List.of("A", "B", "C"))
                .correctAnswer("A")
                .marks(2)
                .build();

        QuestionResponse res = QuestionResponse.builder()
                .id(1L)
                .assessmentId(1L)
                .questionText("Sample Question")
                .options(List.of("A", "B", "C"))
                .correctAnswer("A")
                .marks(2)
                .build();

        when(questionService.create(any(QuestionRequest.class))).thenReturn(res);

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.questionText").value("Sample Question"));

        verify(questionService, times(1)).create(any(QuestionRequest.class));
    }

    @Test
    void updateQuestion_returnsOk() throws Exception {
        QuestionRequest req = QuestionRequest.builder()
                .assessmentId(1L)
                .questionText("Updated Question")
                .options(List.of("A", "B", "C"))
                .correctAnswer("B")
                .marks(3)
                .build();

        QuestionResponse res = QuestionResponse.builder()
                .id(1L)
                .assessmentId(1L)
                .questionText("Updated Question")
                .options(List.of("A", "B", "C"))
                .correctAnswer("B")
                .marks(3)
                .build();

        when(questionService.update(eq(1L), any(QuestionRequest.class))).thenReturn(res);

        mockMvc.perform(put("/api/questions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionText").value("Updated Question"))
                .andExpect(jsonPath("$.marks").value(3));

        verify(questionService, times(1)).update(eq(1L), any(QuestionRequest.class));
    }

    @Test
    void deleteQuestion_returnsNoContent() throws Exception {
        doNothing().when(questionService).delete(1L);

        mockMvc.perform(delete("/api/questions/1"))
                .andExpect(status().isNoContent());

        verify(questionService, times(1)).delete(1L);
    }

    @Test
    void listByAssessment_returnsOk() throws Exception {
        QuestionResponse res = QuestionResponse.builder()
                .id(1L)
                .assessmentId(1L)
                .questionText("Sample Question")
                .options(List.of("A", "B"))
                .correctAnswer(null) // user view hides answer
                .marks(1)
                .build();

        when(questionService.listByAssessment(1L)).thenReturn(List.of(res));

        mockMvc.perform(get("/api/questions/assessment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].correctAnswer").doesNotExist());

        verify(questionService, times(1)).listByAssessment(1L);
    }

    @Test
    void listByAssessmentForAdmin_returnsOk() throws Exception {
        QuestionResponse res = QuestionResponse.builder()
                .id(1L)
                .assessmentId(1L)
                .questionText("Sample Question")
                .options(List.of("A", "B"))
                .correctAnswer("A")
                .marks(1)
                .build();

        when(questionService.listByAssessmentForAdmin(1L)).thenReturn(List.of(res));

        mockMvc.perform(get("/api/questions/admin/assessment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].correctAnswer").value("A"));

        verify(questionService, times(1)).listByAssessmentForAdmin(1L);
    }
}