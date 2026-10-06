package com.app.dethloff.service;

import com.app.dethloff.dao.LessonDAO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;
import com.app.dethloff.model.DTO.mappers.LessonMapper;
import com.app.dethloff.model.LessonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService{
    LessonMapper lessonMapper;
    LessonDAO lessonDAO;

    @Autowired
    LessonServiceImpl(LessonMapper lessonMapper, LessonDAO lessonDAO) {
        this.lessonMapper = lessonMapper;
        this.lessonDAO = lessonDAO;
    }

    public DetailedLessonDTO get(String id) {
        Optional<LessonEntity> lessonEntity = lessonDAO.findById(id);
        DetailedLessonDTO detailedLesson = null;
        if (lessonEntity.isPresent()) {
            detailedLesson = lessonMapper.toDetailedDTO(lessonEntity.get());
        }
        return detailedLesson;
    }
}
