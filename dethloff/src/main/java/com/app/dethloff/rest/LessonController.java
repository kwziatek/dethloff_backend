package com.app.dethloff.rest;

import com.app.dethloff.model.DTO.LessonRequestDTO;
import com.app.dethloff.model.DTO.LessonResponseDTO;
import com.app.dethloff.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LessonController {
    private LessonService lessonService;

    @Autowired
    LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/lesson/{lessonId}")
    ResponseEntity<LessonResponseDTO> getLesson(@PathVariable String lessonId) {
        LessonResponseDTO response = lessonService.get(lessonId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/lesson/course/{courseId}")
    ResponseEntity<List<LessonResponseDTO>> getLessonsAssignedToCourse(@PathVariable String courseId) {
        List<LessonResponseDTO> response = lessonService.getAllFromCourse(courseId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/lesson")
    ResponseEntity<List<LessonResponseDTO>> getAllLessons() {
        List<LessonResponseDTO> response = lessonService.getAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/lesson")
    ResponseEntity<LessonResponseDTO> createLesson(@RequestBody LessonRequestDTO lessonDTO) {
        LessonResponseDTO response = lessonService.create(lessonDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/lesson/{lessonId}")
    ResponseEntity<LessonResponseDTO> deleteLesson(@PathVariable String lessonId) {
        lessonService.delete(lessonId);

        return ResponseEntity.noContent().build();
    }
}
