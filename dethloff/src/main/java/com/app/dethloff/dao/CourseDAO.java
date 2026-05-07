package com.app.dethloff.dao;

import com.app.dethloff.model.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    CourseEntity save(CourseEntity course);
    CourseEntity update(CourseEntity course);
    Optional<CourseEntity> findById(String id);
    Optional<List<CourseEntity>> findAll();
    void remove(CourseEntity course);
    Optional<List<CourseEntity>> findAllBySetId(String setOfCoursesId);
}
