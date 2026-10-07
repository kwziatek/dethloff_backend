package com.app.dethloff.service;


import com.app.dethloff.model.DTO.BasicLessonDTO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;

public interface LessonService {
    DetailedLessonDTO get(String id);
    DetailedLessonDTO create(BasicLessonDTO lessonDTO);
}
