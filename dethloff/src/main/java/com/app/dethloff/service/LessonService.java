package com.app.dethloff.service;

import com.app.dethloff.model.DTO.LessonRequestDTO;
import com.app.dethloff.model.DTO.LessonResponseDTO;

import java.util.List;

public interface LessonService {
    LessonResponseDTO create(LessonRequestDTO lessonRequestDTO);
    LessonResponseDTO get(String id);
    List<LessonResponseDTO> getAll();
    LessonResponseDTO update(LessonRequestDTO lessonRequestDTO);
    void delete(String lessonId);
    List<LessonResponseDTO> getAllFromCourse(String courseId);
}
