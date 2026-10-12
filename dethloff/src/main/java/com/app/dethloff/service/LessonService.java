package com.app.dethloff.service;

import com.app.dethloff.model.DTO.BasicLessonDTO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;

import java.util.List;

public interface LessonService {
    DetailedLessonDTO create(BasicLessonDTO lessonDTO);
    DetailedLessonDTO get(String id);
    List<BasicLessonDTO> getAll();
    DetailedLessonDTO update(BasicLessonDTO lessonDTO);
    void delete(String lessonId);
    List<DetailedLessonDTO> getAllFromCourse(String courseId);
}
