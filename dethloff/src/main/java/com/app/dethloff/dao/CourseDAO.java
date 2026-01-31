package com.app.dethloff.dao;

import com.app.dethloff.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    Course save(Course course);
    Course update(Course course);
    Optional<Course> findById(String id);
    Optional<List<Course>> findAll();
    void remove(Course course);
}
