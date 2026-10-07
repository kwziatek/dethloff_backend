package com.app.dethloff.rest;

import com.app.dethloff.model.DTO.BasicLessonDTO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;
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
    ResponseEntity<DetailedLessonDTO> getLesson(@PathVariable String lessonId) {
        DetailedLessonDTO response = lessonService.get(lessonId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/lesson/course/{courseId}")
    ResponseEntity<List<DetailedLessonDTO>> getLessonsAssignedToCourse(@PathVariable String courseId) {
        List<DetailedLessonDTO> response = lessonService.getAllFromCourse(courseId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/lesson")
    ResponseEntity<List<DetailedLessonDTO>> getAllLessons() {
        List<DetailedLessonDTO> response = lessonService.getAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/lesson")
    ResponseEntity<DetailedLessonDTO> createLesson(@RequestBody BasicLessonDTO lessonDTO) {
        DetailedLessonDTO response = lessonService.create(lessonDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/lesson/{lessonId}")
    ResponseEntity<DetailedLessonDTO> deleteLesson(@PathVariable String lessonId) {
        lessonService.delete(lessonId);

        return ResponseEntity.noContent().build();
    }
}
