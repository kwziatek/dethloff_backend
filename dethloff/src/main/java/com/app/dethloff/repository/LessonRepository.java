package com.app.dethloff.repository;

import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, String> {
    List<LessonEntity> findAllByCourse_Id(String courseId);
    List<LessonEntity> findAllByCourse(CourseEntity course);
}
