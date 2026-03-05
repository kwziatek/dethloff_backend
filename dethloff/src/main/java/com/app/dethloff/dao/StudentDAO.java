package com.app.dethloff.dao;

import com.app.dethloff.model.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    StudentEntity save(StudentEntity theStudent);
    StudentEntity update(StudentEntity student);
    Optional<StudentEntity> findById(String theId);
    Optional<List<StudentEntity>> findAll();
    void remove(StudentEntity theStudent);
}
