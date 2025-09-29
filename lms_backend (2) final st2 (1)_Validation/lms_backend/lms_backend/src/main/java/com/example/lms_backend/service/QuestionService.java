//package com.example.lms_backend.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.example.lms_backend.dto.question.QuestionRequest;
//import com.example.lms_backend.dto.question.QuestionResponse;
//import com.example.lms_backend.exception.ApiException;
//import com.example.lms_backend.model.Question;
//import com.example.lms_backend.repository.QuestionRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class QuestionService {
//
//    private final QuestionRepository questionRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public QuestionResponse create(QuestionRequest req) {
//        try {
//            String optionsJson = objectMapper.writeValueAsString(req.getOptions());
//            Question q = Question.builder()
//                    .assessmentId(req.getAssessmentId())
//                    .questionText(req.getQuestionText())
//                    .options(optionsJson)
//                    .correctAnswer(req.getCorrectAnswer())
//                    .marks(req.getMarks() == null ? 1 : req.getMarks())
//                    .build();
//            questionRepository.save(q);
//            return toResponse(q);
//        } catch (JsonProcessingException e) {
//            throw new ApiException("Invalid options format");
//        }
//    }
//
//    public List<QuestionResponse> listByAssessment(Long assessmentId) {
//        return questionRepository.findByAssessmentId(assessmentId).stream().map(this::toResponseNoAnswer).toList();
//    }
//
//    // for admin: include correctAnswer in response
//    public List<QuestionResponse> listByAssessmentForAdmin(Long assessmentId) {
//        return questionRepository.findByAssessmentId(assessmentId).stream().map(this::toResponse).toList();
//    }
//
//    public Question getEntity(Long id) {
//        return questionRepository.findById(id).orElseThrow(() -> new ApiException("Question not found"));
//    }
//
//    private QuestionResponse toResponse(Question q) {
//        try {
//            List<String> opts = objectMapper.readValue(q.getOptions(), List.class);
//            return QuestionResponse.builder()
//                    .id(q.getId())
//                    .assessmentId(q.getAssessmentId())
//                    .questionText(q.getQuestionText())
//                    .options(opts)
//                    .correctAnswer(q.getCorrectAnswer())
//                    .marks(q.getMarks())
//                    .build();
//        } catch (JsonProcessingException e) {
//            throw new ApiException("Invalid stored options");
//        }
//    }
//
//    // hide correctAnswer from normal list (for user)
//    private QuestionResponse toResponseNoAnswer(Question q) {
//        QuestionResponse r = toResponse(q);
//        r.setCorrectAnswer(null);
//        return r;
//    }
//}






package com.example.lms_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lms_backend.dto.question.QuestionRequest;
import com.example.lms_backend.dto.question.QuestionResponse;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.model.Question;
import com.example.lms_backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuestionResponse create(QuestionRequest req) {
        try {
            String optionsJson = objectMapper.writeValueAsString(req.getOptions());
            Question q = Question.builder()
                    .assessmentId(req.getAssessmentId())
                    .questionText(req.getQuestionText())
                    .options(optionsJson)
                    .correctAnswer(req.getCorrectAnswer())
                    .marks(req.getMarks() == null ? 1 : req.getMarks())
                    .build();
            questionRepository.save(q);
            return toResponse(q);
        } catch (JsonProcessingException e) {
            throw new ApiException("Invalid options format");
        }
    }

    public QuestionResponse update(Long id, QuestionRequest req) {
        Question q = questionRepository.findById(id).orElseThrow(() -> new ApiException("Question not found"));
        try {
            String optionsJson = objectMapper.writeValueAsString(req.getOptions());
            q.setAssessmentId(req.getAssessmentId());
            q.setQuestionText(req.getQuestionText());
            q.setOptions(optionsJson);
            q.setCorrectAnswer(req.getCorrectAnswer());
            q.setMarks(req.getMarks() == null ? 1 : req.getMarks());
            questionRepository.save(q);
            return toResponse(q);
        } catch (JsonProcessingException e) {
            throw new ApiException("Invalid options format");
        }
    }

    public void delete(Long id) {
        Question q = questionRepository.findById(id).orElseThrow(() -> new ApiException("Question not found"));
        questionRepository.delete(q);
    }

    public List<QuestionResponse> listByAssessment(Long assessmentId) {
        return questionRepository.findByAssessmentId(assessmentId).stream()
                .map(this::toResponseNoAnswer)
                .toList();
    }

    public List<QuestionResponse> listByAssessmentForAdmin(Long assessmentId) {
        return questionRepository.findByAssessmentId(assessmentId).stream()
                .map(this::toResponse)
                .toList();
    }

    public Question getEntity(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ApiException("Question not found"));
    }

    private QuestionResponse toResponse(Question q) {
        try {
            List<String> opts = objectMapper.readValue(q.getOptions(), List.class);
            return QuestionResponse.builder()
                    .id(q.getId())
                    .assessmentId(q.getAssessmentId())
                    .questionText(q.getQuestionText())
                    .options(opts)
                    .correctAnswer(q.getCorrectAnswer())
                    .marks(q.getMarks())
                    .build();
        } catch (JsonProcessingException e) {
            throw new ApiException("Invalid stored options");
        }
    }

    private QuestionResponse toResponseNoAnswer(Question q) {
        QuestionResponse r = toResponse(q);
        r.setCorrectAnswer(null);
        return r;
    }
}
