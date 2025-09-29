

package com.example.lms_backend.repository;

import com.example.lms_backend.model.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    List<AssessmentResult> findByUserId(Long userId);
    List<AssessmentResult> findByAssessmentId(Long assessmentId);


    Optional<AssessmentResult> findByUserIdAndAssessmentId(Long userId, Long assessmentId);
}

//package com.example.lms_backend.repository;
//
//import com.example.lms_backend.model.AssessmentResult;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
//
//    List<AssessmentResult> findByUserId(Long userId);
//    List<AssessmentResult> findByAssessmentId(Long assessmentId);
//
//    Optional<AssessmentResult> findByUserIdAndAssessmentId(Long userId, Long assessmentId);
//
//    // ✅ Top scorers in a specific course
//    @Query("SELECT ar.userId, SUM(ar.score) as totalScore " +
//            "FROM AssessmentResult ar " +
//            "JOIN Assessment a ON ar.assessmentId = a.id " +
//            "WHERE a.courseId = :courseId " +
//            "GROUP BY ar.userId " +
//            "ORDER BY totalScore DESC")
//    List<Object[]> getLeaderboardByCourse(Long courseId);
//
//    // ✅ Global leaderboard across all courses
//    @Query("SELECT ar.userId, SUM(ar.score) as totalScore " +
//            "FROM AssessmentResult ar " +
//            "GROUP BY ar.userId " +
//            "ORDER BY totalScore DESC")
//    List<Object[]> getGlobalLeaderboard();
//}