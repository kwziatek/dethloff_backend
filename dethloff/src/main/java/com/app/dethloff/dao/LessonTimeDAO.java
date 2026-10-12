package com.app.dethloff.dao;

import com.app.dethloff.model.LessonTimeEntity;
import java.util.Optional;

public interface LessonTimeDAO {
    LessonTimeEntity save(LessonTimeEntity entity);
    LessonTimeEntity update(LessonTimeEntity entity);
    Optional<LessonTimeEntity> findById(String id);
    Optional<LessonTimeEntity> findByLessonId(String lessonId);
    void remove(LessonTimeEntity entity);
}
