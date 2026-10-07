package com.app.dethloff.dao;

import com.app.dethloff.model.LessonEntity;

import java.util.List;
import java.util.Optional;

public interface LessonDAO {
    LessonEntity save(LessonEntity lesson);
    LessonEntity update(LessonEntity lesson);
    Optional<LessonEntity> findById(String id);
    List<LessonEntity> findAll();
    void remove(LessonEntity lesson);
    LessonEntity createProxy(String id);

    List<LessonEntity> findAllByCourseId(String courseId);
}
