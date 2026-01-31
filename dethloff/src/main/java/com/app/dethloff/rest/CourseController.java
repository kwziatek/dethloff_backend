package com.app.dethloff.rest;

import com.app.dethloff.model.DTO.CourseRequestDTO;
import com.app.dethloff.model.DTO.CourseResponseDTO;
import com.app.dethloff.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponseDTO>> getCourses() {
        List<CourseResponseDTO> courses = courseService.getAll();

        return ResponseEntity.ok(courses);
    }

    @GetMapping("courses/{courseId}")
    public ResponseEntity<CourseResponseDTO> getCourse(@PathVariable String courseId) {
        CourseResponseDTO courseResponseDTO = courseService.get(courseId);

        return ResponseEntity.ok(courseResponseDTO);
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO courseResponseDTO = courseService.create(courseRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(courseResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(courseResponseDTO);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        courseService.delete(courseId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/courses")
    public ResponseEntity<CourseResponseDTO> updateCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO courseResponseDTO = courseService.update(courseRequestDTO);

        return ResponseEntity.ok(courseResponseDTO);
    }

    @PostMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<?> enrollStudent(@PathVariable String courseId, @PathVariable String studentId) {
        courseService.enrollStudent(courseId, studentId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<?> unenrollStudent(@PathVariable String courseId, @PathVariable String studentId) {
        courseService.unenrollStudent(courseId, studentId);

        return ResponseEntity.noContent().build();
    }
}
