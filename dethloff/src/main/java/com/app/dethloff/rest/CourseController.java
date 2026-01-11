package com.app.dethloff.rest;

import com.app.dethloff.DTO.CourseDTO;
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
    public ResponseEntity<List<CourseDTO>> getCourses() {
        List<CourseDTO> courses = courseService.getAll();

        return ResponseEntity.ok(courses);
    }

    @GetMapping("courses/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable String courseId) {
        CourseDTO courseDTO = courseService.get(courseId);

        return ResponseEntity.ok(courseDTO);
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO responseCourseDTO = courseService.create(courseDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseCourseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseCourseDTO);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        courseService.delete(courseId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/courses")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO responseCourseDTO = courseService.update(courseDTO);

        return ResponseEntity.ok(responseCourseDTO);
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
