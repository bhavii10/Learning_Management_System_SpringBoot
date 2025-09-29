//package com.example.lms_backend.controller;
//
//import com.example.lms_backend.model.Course;
//import com.example.lms_backend.model.Lesson;
//import com.example.lms_backend.repository.CourseRepository;
//import com.example.lms_backend.repository.LessonRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/lessons")
//@RequiredArgsConstructor
//public class LessonController {
//
//    private final LessonRepository lessonRepository;
//    private final CourseRepository courseRepository;
//
//    /**
//     * Toggle completion of a lesson and update course progress
//     */
//    @PutMapping("/{lessonId}/toggle")
//    public ResponseEntity<Lesson> toggleLesson(
//            @PathVariable Long lessonId,
//            @RequestBody Map<String, Boolean> body) {
//
//        Lesson lesson = lessonRepository.findById(lessonId)
//                .orElseThrow(() -> new RuntimeException("Lesson not found"));
//
//        // ✅ update lesson completion
//        lesson.setCompleted(body.get("completed"));
//        lessonRepository.save(lesson);
//
//        // ✅ recalc course progress
//        Course course = lesson.getCourse();
//        long total = course.getLessons().size();
//        long completed = course.getLessons()
//                .stream()
//                .filter(Lesson::isCompleted)
//                .count();
//
//        int progress = (int) ((completed * 100.0) / total);
//        course.setProgress(progress);
//        courseRepository.save(course);
//
//        return ResponseEntity.ok(lesson);
//    }
//}
package com.example.lms_backend.controller;

import com.example.lms_backend.model.Course;
import com.example.lms_backend.model.Lesson;
import com.example.lms_backend.repository.CourseRepository;
import com.example.lms_backend.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    /**
     * Toggle completion of a lesson and update course progress
     */
    @PutMapping("/{lessonId}/toggle")
    public ResponseEntity<Map<String, Object>> toggleLesson(
            @PathVariable Long lessonId,
            @RequestBody Map<String, Boolean> body) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Lesson not found"));

        // ✅ update lesson completion
        lesson.setCompleted(body.get("completed"));
        lessonRepository.save(lesson);

        // ✅ recalc course progress
        Course course = lesson.getCourse();
        long total = course.getLessons().size();
        long completed = course.getLessons()
                .stream()
                .filter(Lesson::isCompleted)
                .count();

        int progress = total == 0 ? 0 : (int) ((completed * 100.0) / total);
        course.setProgress(progress);
        courseRepository.save(course);

        // ✅ return both updated lesson + progress
        return ResponseEntity.ok(Map.of(
                "lessonId", lesson.getId(),
                "completed", lesson.isCompleted(),
                "courseProgress", progress
        ));
    }



}
