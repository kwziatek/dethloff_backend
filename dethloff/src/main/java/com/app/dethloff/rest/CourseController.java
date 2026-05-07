package com.app.dethloff.rest;

import com.app.dethloff.model.DTO.BasicCourseDTO;
import com.app.dethloff.model.DTO.DetailedCourseDTO;
import com.app.dethloff.model.DTO.SetOfCoursesDTO;
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

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<DetailedCourseDTO>> getAllCourses() {
        List<DetailedCourseDTO> courses = courseService.getAll();

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/setOfCourses/{setOfCoursesId}")
    public ResponseEntity<List<DetailedCourseDTO>> getAllSetCourses(@PathVariable String setOfCoursesId) {
        List<DetailedCourseDTO> courses = courseService.getAllFromParticularSet(setOfCoursesId);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("courses/{courseId}")
    public ResponseEntity<DetailedCourseDTO> getCourse(@PathVariable String courseId) {
        DetailedCourseDTO detailedCourseDTO = courseService.get(courseId);

        return ResponseEntity.ok(detailedCourseDTO);
    }

    @PostMapping("/courses")
    public ResponseEntity<DetailedCourseDTO> createCourse(@RequestBody BasicCourseDTO basicCourseDTO) {
        DetailedCourseDTO detailedCourseDTO = courseService.create(basicCourseDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(detailedCourseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(detailedCourseDTO);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        courseService.delete(courseId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/courses")
    public ResponseEntity<DetailedCourseDTO> updateCourse(@RequestBody BasicCourseDTO basicCourseDTO) {
        DetailedCourseDTO detailedCourseDTO = courseService.update(basicCourseDTO);

        return ResponseEntity.ok(detailedCourseDTO);
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

    @GetMapping("courses/setOfCourses")
    public ResponseEntity<?> getAllSetsOfCourses() {
        List<SetOfCoursesDTO> setOfCoursesDTOS = courseService.getAllSetsOfCourses();

        return ResponseEntity.ok(setOfCoursesDTOS);
    }
}
