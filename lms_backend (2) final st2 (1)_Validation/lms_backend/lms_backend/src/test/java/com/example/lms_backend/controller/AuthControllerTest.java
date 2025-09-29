package com.example.lms_backend.controller;

import com.example.lms_backend.dto.*;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new com.example.lms_backend.exception.GlobalExceptionHandler())
                .build();
    }

    // ------------------- SUCCESS CASES -------------------
    @Test
    void testRegister_Success() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .name("Dhruv")
                .email("dhruv@example.com")
                .password("password123")
                .build();

        LoginResponse response = LoginResponse.builder()
                .id(1L)
                .name("Dhruv")
                .email("dhruv@example.com")
                .role(null)
                .token("dummy-token")
                .build();

        Mockito.when(userService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("dummy-token"))
                .andExpect(jsonPath("$.email").value("dhruv@example.com"))
                .andExpect(jsonPath("$.name").value("Dhruv"));
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("dhruv@example.com")
                .password("password123")
                .build();

        LoginResponse response = LoginResponse.builder()
                .id(1L)
                .name("Dhruv")
                .email("dhruv@example.com")
                .role(null)
                .token("login-token")
                .build();

        Mockito.when(userService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("login-token"))
                .andExpect(jsonPath("$.email").value("dhruv@example.com"))
                .andExpect(jsonPath("$.name").value("Dhruv"));
    }

    // ------------------- EXCEPTION CASES -------------------
    @Test
    void testRegister_EmailAlreadyExists() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .name("Dhruv")
                .email("dhruv@example.com")
                .password("password123")
                .build();

        doThrow(new ApiException("Email already registered"))
                .when(userService).register(any(RegisterRequest.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already registered"));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("dhruv@example.com")
                .password("wrongpassword")
                .build();

        doThrow(new ApiException("Invalid email or password"))
                .when(userService).login(any(LoginRequest.class));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }

    // ------------------- VALIDATION CASES -------------------
    @Test
    void testRegister_ValidationError() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .name("")  // invalid
                .email("invalid-email") // invalid
                .password("")
                .build();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testLogin_ValidationError() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("invalid-email")
                .password("")
                .build();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
