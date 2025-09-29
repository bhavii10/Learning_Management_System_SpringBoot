//package com.example.lms_backend.controller;
//
//import com.example.lms_backend.dto.result.SubmissionRequest;
//import com.example.lms_backend.dto.result.SubmissionResponse;
//import com.example.lms_backend.service.ResultService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/results")
//@RequiredArgsConstructor
//public class ResultController {
//
//    private final ResultService resultService;
//
//    // User submits answers; server calculates score and stores
//    @PostMapping("/submit")
//    public ResponseEntity<SubmissionResponse> submit(@RequestBody SubmissionRequest req) {
//        SubmissionResponse resp = resultService.submit(req);
//        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
//    }
//}

package com.example.lms_backend.controller;

import com.example.lms_backend.dto.result.SubmissionRequest;
import com.example.lms_backend.dto.result.SubmissionResponse;
import com.example.lms_backend.model.AssessmentResult;
import com.example.lms_backend.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    // ---------------- POST ----------------
    @PostMapping("/submit")
    public ResponseEntity<SubmissionResponse> submit(@RequestBody SubmissionRequest req) {
        SubmissionResponse resp = resultService.submit(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // ---------------- GET ----------------
    @GetMapping("/{resultId}")
    public ResponseEntity<AssessmentResult> getResultById(@PathVariable Long resultId) {
        return ResponseEntity.ok(resultService.getResultById(resultId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssessmentResult>> getResultsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(resultService.getResultsByUser(userId));
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<AssessmentResult>> getResultsByAssessment(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(resultService.getResultsByAssessment(assessmentId));
    }

    @GetMapping("/user/{userId}/assessment/{assessmentId}")
    public ResponseEntity<AssessmentResult> getResultForUserAndAssessment(
            @PathVariable Long userId,
            @PathVariable Long assessmentId) {
        return ResponseEntity.ok(resultService.getResultForUserAndAssessment(userId, assessmentId));
    }
}