package com.example.lms_backend.controller;

import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.exception.GlobalExceptionHandler;
import com.example.lms_backend.model.Announcement;
import com.example.lms_backend.service.AnnouncementService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AnnouncementController controller;

    @Mock
    private AnnouncementService service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        // Register Java 8 time module
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAllAnnouncements() throws Exception {
        Announcement a1 = Announcement.builder()
                .id(1)
                .title("Test1")
                .author("Author1")
                .content("Content1")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .build();

        Announcement a2 = Announcement.builder()
                .id(2)
                .title("Test2")
                .author("Author2")
                .content("Content2")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .build();

        when(service.getAllAnnouncements()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/api/announcements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test1"))
                .andExpect(jsonPath("$[1].title").value("Test2"));
    }

    @Test
    void testGetAnnouncementByIdNotFound() throws Exception {
        when(service.getAnnouncementById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/announcements/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAnnouncementException() throws Exception {
        Announcement updated = Announcement.builder()
                .title("Updated")
                .content("UpdatedContent")
                .author("Author1")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .build();

        when(service.updateAnnouncement(eq(999), any(Announcement.class)))
                .thenThrow(new ApiException("Announcement not found"));

        mockMvc.perform(put("/api/announcements/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Announcement not found"));
    }
}