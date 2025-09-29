package com.example.lms_backend.dto;

import com.example.lms_backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String token; // token will be present for registration/login
}