//package com.example.lms_backend.controller;
//
//import com.example.lms_backend.dto.question.QuestionRequest;
//import com.example.lms_backend.dto.question.QuestionResponse;
//import com.example.lms_backend.service.QuestionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/questions")
//@RequiredArgsConstructor
//public class QuestionController {
//
//    private final QuestionService questionService;
//
//    // Admin: add question (can also do via Postman)
//    @PostMapping
//    public ResponseEntity<QuestionResponse> create(@RequestBody QuestionRequest req) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.create(req));
//    }
//
//    // List questions for users - hides correct answer
//    @GetMapping("/assessment/{assessmentId}")
//    public ResponseEntity<List<QuestionResponse>> listForUser(@PathVariable Long assessmentId) {
//        return ResponseEntity.ok(questionService.listByAssessment(assessmentId));
//    }
//
//    // Admin: list with answers
//    @GetMapping("/admin/assessment/{assessmentId}")
//    public ResponseEntity<List<QuestionResponse>> listForAdmin(@PathVariable Long assessmentId) {
//        return ResponseEntity.ok(questionService.listByAssessmentForAdmin(assessmentId));
//    }
//}



package com.example.lms_backend.controller;

import com.example.lms_backend.dto.question.QuestionRequest;
import com.example.lms_backend.dto.question.QuestionResponse;
import com.example.lms_backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // Admin: add question
    @PostMapping
    public ResponseEntity<QuestionResponse> create(@RequestBody QuestionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.create(req));
    }

    // Admin: update question
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> update(@PathVariable Long id, @RequestBody QuestionRequest req) {
        return ResponseEntity.ok(questionService.update(id, req));
    }

    // Admin: delete question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // List questions for users (without correctAnswer)
    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<QuestionResponse>> listForUser(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(questionService.listByAssessment(assessmentId));
    }

    // Admin: list with answers
    @GetMapping("/admin/assessment/{assessmentId}")
    public ResponseEntity<List<QuestionResponse>> listForAdmin(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(questionService.listByAssessmentForAdmin(assessmentId));
    }
}
