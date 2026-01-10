package com.app.dethloff.rest;

import com.app.dethloff.DTO.CourseDTO;
import com.app.dethloff.model.Course;
import com.app.dethloff.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<CourseDTO> getCourses() {
        return courseService.getAll();
    }

    @GetMapping("courses/{courseId}")
    public CourseDTO getCourse(@PathVariable String courseId) {
        return courseService.get(courseId);
    }

    @PostMapping("/courses")
    public void createCourse(@RequestBody CourseDTO courseDTO) {
        courseService.create(courseDTO);
    }

    @DeleteMapping("/courses/{courseId}")
    public void deleteCourse(@PathVariable String courseId) {
        courseService.delete(courseId);
    }

    @PutMapping("/courses")
    public void updateCourse(@RequestBody CourseDTO courseDTO) {
        courseService.update(courseDTO);
    }
}
