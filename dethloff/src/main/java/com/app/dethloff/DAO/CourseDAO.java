package com.app.dethloff.DAO;

import com.app.dethloff.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    void save(Course course);
    void update(Course course);
    Optional<Course> findById(String id);
    Optional<List<Course>> findAll();
    void remove(Course course);
}
