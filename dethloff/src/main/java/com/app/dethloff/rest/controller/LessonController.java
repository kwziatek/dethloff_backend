package com.app.dethloff.rest.controller;

import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import com.app.dethloff.rest.DTO.validation_groups.CreateAction;
import com.app.dethloff.rest.DTO.validation_groups.UpdateAction;
import com.app.dethloff.service.inte.CourseService;
import com.app.dethloff.service.inte.LessonService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class LessonController {
  private LessonService lessonService;
  private CourseService courseService;

  @Autowired
  LessonController(LessonService lessonService, CourseService courseService) {
    this.lessonService = lessonService;
    this.courseService = courseService;
  }

  @GetMapping("/lesson/{lessonId}")
  ResponseEntity<LessonResponseDTO> getLesson(@PathVariable String lessonId) {
    LessonResponseDTO response = lessonService.get(lessonId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/lesson/course/{courseId}")
  ResponseEntity<List<LessonResponseDTO>> getLessonsAssignedToCourse(
      @PathVariable String courseId) {
    List<LessonResponseDTO> response = lessonService.getAllFromCourse(courseId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/lesson")
  ResponseEntity<List<LessonResponseDTO>> getAllLessons() {
    List<LessonResponseDTO> response = lessonService.getAll();

    return ResponseEntity.ok(response);
  }

  @PostMapping("/lesson")
  ResponseEntity<LessonResponseDTO> createLesson(
      @Validated(CreateAction.class) @RequestBody LessonRequestDTO lessonDTO) {
    LessonResponseDTO response = lessonService.create(lessonDTO);
    // since the lesson was created, the start or end date for the course might have changed
    courseService.updateStartAndEndDateForCourse(lessonDTO.courseId());

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @PutMapping("/lesson")
  ResponseEntity<LessonResponseDTO> updateLesson(
      @Validated(UpdateAction.class) @RequestBody LessonRequestDTO lessonDTO) {
    LessonResponseDTO response = lessonService.update(lessonDTO);
    // since the lesson was updated, the start or end date for the course might have changed
    courseService.updateStartAndEndDateForCourse(lessonDTO.courseId());

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/lesson/{lessonId}")
  ResponseEntity<LessonResponseDTO> deleteLesson(@PathVariable String lessonId) {
    lessonService.delete(lessonId);
    // since the lesson was updated, the start or end date for the course might have changed
    courseService.updateStartAndEndDateForCourse(lessonId);

    return ResponseEntity.noContent().build();
  }
}
