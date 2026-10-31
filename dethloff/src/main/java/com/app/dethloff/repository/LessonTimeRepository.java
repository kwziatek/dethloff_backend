package com.app.dethloff.repository;

import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.model.LessonTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonTimeRepository extends JpaRepository<LessonTimeEntity, String> {
    List<LessonTimeEntity> findByLesson_Id(String lessonId);
    List<LessonTimeEntity> findByLesson(LessonEntity lesson);
}
