//package com.example.lms_backend.service;
//
//import com.example.lms_backend.dto.assessment.AssessmentRequest;
//import com.example.lms_backend.dto.assessment.AssessmentResponse;
//import com.example.lms_backend.exception.ApiException;
//import com.example.lms_backend.model.Assessment;
//import com.example.lms_backend.repository.AssessmentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AssessmentService {
//
//    private final AssessmentRepository assessmentRepository;
//
//    public AssessmentResponse create(AssessmentRequest req) {
//        Assessment a = Assessment.builder()
//                .courseId(req.getCourseId())
//                .title(req.getTitle())
//                .build();
//        assessmentRepository.save(a);
//        return toResponse(a);
//    }
//
//    public List<AssessmentResponse> listByCourse(Long courseId) {
//        return assessmentRepository.findByCourseId(courseId).stream().map(this::toResponse).toList();
//    }
//
//    public AssessmentResponse get(Long id) {
//        Assessment a = assessmentRepository.findById(id).orElseThrow(() -> new ApiException("Assessment not found"));
//        return toResponse(a);
//    }
//
//    private AssessmentResponse toResponse(Assessment a) {
//        return AssessmentResponse.builder()
//                .id(a.getId())
//                .courseId(a.getCourseId())
//                .title(a.getTitle())
//                .createdAt(a.getCreatedAt())
//                .build();
//    }
//}

package com.example.lms_backend.service;

import com.example.lms_backend.dto.assessment.AssessmentRequest;
import com.example.lms_backend.dto.assessment.AssessmentResponse;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.model.Assessment;
import com.example.lms_backend.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;

    // ✅ Create assessment
    public AssessmentResponse create(AssessmentRequest req) {
        Assessment a = Assessment.builder()
                .courseId(req.getCourseId())
                .title(req.getTitle())
                .build();
        assessmentRepository.save(a);
        return toResponse(a);
    }

    // ✅ List assessments by course
    public List<AssessmentResponse> listByCourse(Long courseId) {
        return assessmentRepository.findByCourseId(courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ✅ Get assessment by ID
    public AssessmentResponse get(Long id) {
        Assessment a = assessmentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Assessment not found"));
        return toResponse(a);
    }

    // ✅ Update assessment
    public AssessmentResponse update(Long id, AssessmentRequest req) {
        Assessment a = assessmentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Assessment not found"));

        a.setTitle(req.getTitle());
        a.setCourseId(req.getCourseId());
        assessmentRepository.save(a);

        return toResponse(a);
    }

    // ✅ Delete assessment
    public void delete(Long id) {
        Assessment a = assessmentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Assessment not found"));
        assessmentRepository.delete(a);
    }

    // ✅ Convert entity to response DTO
    private AssessmentResponse toResponse(Assessment a) {
        return AssessmentResponse.builder()
                .id(a.getId())
                .courseId(a.getCourseId())
                .title(a.getTitle())
                .createdAt(a.getCreatedAt())
                .build();
    }
}
