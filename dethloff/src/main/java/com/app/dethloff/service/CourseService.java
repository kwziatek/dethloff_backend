package com.app.dethloff.service;

import com.app.dethloff.model.DTO.BasicCourseDTO;
import com.app.dethloff.model.DTO.DetailedCourseDTO;

import java.util.List;

public interface CourseService {
    DetailedCourseDTO create(BasicCourseDTO basicCourseDTO);
    DetailedCourseDTO get(String id);
    List<DetailedCourseDTO> getAll();
    void delete(String id);
    DetailedCourseDTO update(BasicCourseDTO basicCourseDTO);
    void enrollStudent(String courseId, String studentId);
    void unenrollStudent(String courseId, String studentId);
}
