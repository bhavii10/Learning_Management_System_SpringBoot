package com.example.lms_backend.repository;

import com.example.lms_backend.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUserId(Long userId);
    List<Progress> findByCourseId(Long courseId);
}