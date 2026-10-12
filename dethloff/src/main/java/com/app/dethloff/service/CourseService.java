package com.app.dethloff.service;

import com.app.dethloff.model.DTO.CourseRequestDTO;
import com.app.dethloff.model.DTO.CourseResponseDTO;
import com.app.dethloff.model.DTO.SetOfCoursesResponseDTO;

import java.util.List;

public interface CourseService {
    CourseResponseDTO create(CourseRequestDTO courseRequestDTO);
    CourseResponseDTO get(String id);
    List<CourseResponseDTO> getAll();
    CourseResponseDTO update(CourseRequestDTO courseRequestDTO);
    void delete(String id);
    void enrollStudent(String courseId, String studentId);
    void unenrollStudent(String courseId, String studentId);
    List<CourseResponseDTO> getAllFromParticularSet(String setOfCoursesId);
    List<SetOfCoursesResponseDTO> getAllSetsOfCourses();
}
