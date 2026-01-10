package com.app.dethloff.DAO;

import com.app.dethloff.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    void save(Student theStudent);
    void update(Student student);
    Optional<Student> findById(String theId);
    Optional<List<Student>> findAll();
    void remove(Student theStudent);
}
