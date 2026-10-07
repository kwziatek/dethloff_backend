package com.app.dethloff.service;


import com.app.dethloff.model.DTO.BasicLessonDTO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;

import java.util.List;

public interface LessonService {
    DetailedLessonDTO get(String id);
    DetailedLessonDTO create(BasicLessonDTO lessonDTO);
    void delete(String lessonId);

    List<DetailedLessonDTO> getAll();

    List<DetailedLessonDTO> getAllFromCourse(String courseId);
}
