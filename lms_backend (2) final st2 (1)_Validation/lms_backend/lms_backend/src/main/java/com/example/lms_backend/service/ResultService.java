

package com.example.lms_backend.service;

import com.example.lms_backend.dto.result.SubmissionRequest;
import com.example.lms_backend.dto.result.SubmissionResponse;
import com.example.lms_backend.exception.ApiException;
import com.example.lms_backend.model.AssessmentResult;
import com.example.lms_backend.model.Question;
import com.example.lms_backend.repository.AssessmentResultRepository;
import com.example.lms_backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final QuestionRepository questionRepository;
    private final AssessmentResultRepository resultRepository;

    // ---------------- POST ----------------
    public SubmissionResponse submit(SubmissionRequest req) {
        List<Question> questions = questionRepository.findByAssessmentId(req.getAssessmentId());

        Map<Long, Question> qmap = questions.stream()
                .collect(java.util.stream.Collectors.toMap(Question::getId, q -> q));

        int score = 0;
        int total = 0;
        for (Question q : questions) {
            int marks = q.getMarks() == null ? 1 : q.getMarks();
            total += marks;
            String chosen = req.getAnswers().get(q.getId());
            if (chosen != null && chosen.equals(q.getCorrectAnswer())) {
                score += marks;
            }
        }

        AssessmentResult ar = AssessmentResult.builder()
                .assessmentId(req.getAssessmentId())
                .userId(req.getUserId())
                .score(score)
                .totalMarks(total)
                .build();
        resultRepository.save(ar);

        return SubmissionResponse.builder()
                .score(score)
                .totalMarks(total)
                .resultId(ar.getId())
                .build();
    }

    // ---------------- GET ----------------

    public AssessmentResult getResultById(Long resultId) {
        return resultRepository.findById(resultId)
                .orElseThrow(() -> new ApiException("Result not found"));
    }

    public List<AssessmentResult> getResultsByUser(Long userId) {
        return resultRepository.findByUserId(userId);
    }

    public List<AssessmentResult> getResultsByAssessment(Long assessmentId) {
        return resultRepository.findByAssessmentId(assessmentId);
    }

    public AssessmentResult getResultForUserAndAssessment(Long userId, Long assessmentId) {
        return resultRepository.findByUserIdAndAssessmentId(userId, assessmentId)
                .orElseThrow(() -> new ApiException("Result not found for this user in this assessment"));
    }
}