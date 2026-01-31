package com.app.dethloff.service;

import com.app.dethloff.model.DTO.CourseRequestDTO;
import com.app.dethloff.model.DTO.CourseResponseDTO;

import java.util.List;

public interface CourseService {
    CourseResponseDTO create(CourseRequestDTO courseRequestDTO);
    CourseResponseDTO get(String id);
    List<CourseResponseDTO> getAll();
    void delete(String id);
    CourseResponseDTO update(CourseRequestDTO courseRequestDTO);
    void enrollStudent(String courseId, String studentId);
    void unenrollStudent(String courseId, String studentId);
}
