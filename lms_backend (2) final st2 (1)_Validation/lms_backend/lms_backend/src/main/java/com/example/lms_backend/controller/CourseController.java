package com.example.lms_backend.controller;

import com.example.lms_backend.dto.course.CourseRequest;
import com.example.lms_backend.dto.course.CourseResponse;
import com.example.lms_backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Admin: Create course
    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest request) {
        CourseResponse resp = courseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // All: List courses
    @GetMapping
    public ResponseEntity<List<CourseResponse>> list() {
        return ResponseEntity.ok(courseService.findAll());
    }

    // All: Course details
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    // Admin: Update
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.update(id, request));
    }

    // Admin: Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}