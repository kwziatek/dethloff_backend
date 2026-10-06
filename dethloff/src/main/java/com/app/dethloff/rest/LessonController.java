package com.app.dethloff.rest;

import com.app.dethloff.model.DTO.DetailedLessonDTO;
import com.app.dethloff.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
