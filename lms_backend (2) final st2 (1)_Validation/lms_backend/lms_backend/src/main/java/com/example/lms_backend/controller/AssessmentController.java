
package com.example.lms_backend.controller;

import com.example.lms_backend.dto.assessment.AssessmentRequest;
import com.example.lms_backend.dto.assessment.AssessmentResponse;
import com.example.lms_backend.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    // ✅ Create assessment
    @PostMapping
    public ResponseEntity<AssessmentResponse> create(@RequestBody AssessmentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentService.create(req));
    }

    // ✅ List assessments by course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssessmentResponse>> listByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(assessmentService.listByCourse(courseId));
    }

    // ✅ Get single assessment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(assessmentService.get(id));
    }

    // ✅ Update assessment
    @PutMapping("/{id}")
    public ResponseEntity<AssessmentResponse> update(
            @PathVariable Long id,
            @RequestBody AssessmentRequest req
    ) {
        return ResponseEntity.ok(assessmentService.update(id, req));
    }

    // ✅ Delete assessment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assessmentService.delete(id);
        return ResponseEntity.noContent().build(); // returns 204
    }
}
