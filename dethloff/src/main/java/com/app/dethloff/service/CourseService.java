package com.app.dethloff.service;

import com.app.dethloff.DTO.CourseDTO;

import java.util.List;

public interface CourseService {
    CourseDTO create(CourseDTO courseDTO);
    CourseDTO get(String id);
    List<CourseDTO> getAll();
    void delete(String id);
    CourseDTO update(CourseDTO courseDTO);
    void enrollStudent(String courseId, String studentId);
    void unenrollStudent(String courseId, String studentId);
}
