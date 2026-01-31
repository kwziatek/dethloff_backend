package com.app.dethloff.dao;

import com.app.dethloff.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    Student save(Student theStudent);
    Student update(Student student);
    Optional<Student> findById(String theId);
    Optional<List<Student>> findAll();
    void remove(Student theStudent);
}
