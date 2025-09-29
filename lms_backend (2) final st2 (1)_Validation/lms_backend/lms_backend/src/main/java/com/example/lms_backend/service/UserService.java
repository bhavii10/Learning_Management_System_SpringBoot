////Pass Validation
//
//package com.example.lms_backend.service;
//
//import com.example.lms_backend.dto.*;
//import com.example.lms_backend.exception.ApiException;
//import com.example.lms_backend.model.Role;
//import com.example.lms_backend.model.User;
//import com.example.lms_backend.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.regex.Pattern;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class UserService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder encoder;
//
//    // Regex for password validation
//    private static final Pattern PASSWORD_PATTERN =
//            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
//
//    public LoginResponse register(RegisterRequest req) {
//        if (userRepository.existsByEmail(req.getEmail())) {
//            log.warn("Email already registered: {}", req.getEmail());
//            throw new ApiException("Email already registered");
//        }
//
//        // validate password strength
//        if (!PASSWORD_PATTERN.matcher(req.getPassword()).matches()) {
//            log.warn("Weak password attempted for email: {}", req.getEmail());
//            throw new ApiException("Password must be at least 8 characters, include uppercase, lowercase, number, and special character.");
//        }
//
//        Role role = req.getRole() == null ? Role.USER : req.getRole();
//        User user = User.builder()
//                .name(req.getName())
//                .email(req.getEmail())
//                .passwordHash(encoder.encode(req.getPassword()))
//                .role(role)
//                .build();
//        userRepository.save(user);
//        log.info("Registered: {}", user.getEmail());
//
//        // JWT removed, just returning user details
//        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), null);
//    }
//
//    public LoginResponse login(LoginRequest req) {
//        User user = userRepository.findByEmail(req.getEmail())
//                .orElseThrow(() -> {
//                    log.warn("Email not found: {}", req.getEmail());
//                    return new ApiException("Invalid email or password");
//                });
//        if (!encoder.matches(req.getPassword(), user.getPasswordHash())) {
//            log.warn("Password mismatch: {}", req.getEmail());
//            throw new ApiException("Invalid email or password");
//        }
//        log.info("Logged in: {}", user.getEmail());
//
//        // JWT removed, just returning user details
//        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), null);
//    }
//
//    // ✅ New method to fetch all users
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//}





package com.example.lms_backend.service;

import com.example.lms_backend.dto.*;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.model.Role;
import com.example.lms_backend.model.User;
import com.example.lms_backend.repository.UserRepository;
import com.example.lms_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    // Registration: generate token, store it, return
    public LoginResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            log.warn("Email already registered: {}", req.getEmail());
            throw new ApiException("Email already registered");
        }

        if (!PASSWORD_PATTERN.matcher(req.getPassword()).matches()) {
            log.warn("Weak password attempted for email: {}", req.getEmail());
            throw new ApiException("Password must be at least 8 characters, include uppercase, lowercase, number, and special character.");
        }

        Role role = req.getRole() == null ? Role.USER : req.getRole();
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .passwordHash(encoder.encode(req.getPassword()))
                .role(role)
                .build();

        // Generate token and store in user
        String token = jwtUtil.generateToken(user.getEmail());
        user.setToken(token);

        userRepository.save(user);
        log.info("Registered: {}", user.getEmail());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }

    // Login: return same token stored in user
    public LoginResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> {
                    log.warn("Email not found: {}", req.getEmail());
                    return new ApiException("Invalid email or password");
                });

        if (!encoder.matches(req.getPassword(), user.getPasswordHash())) {
            log.warn("Password mismatch: {}", req.getEmail());
            throw new ApiException("Invalid email or password");
        }

        log.info("Logged in: {}", user.getEmail());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getToken() // return the token generated at registration
        );
    }

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}