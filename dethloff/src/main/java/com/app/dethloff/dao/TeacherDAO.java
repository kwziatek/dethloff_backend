package com.app.dethloff.dao;

import com.app.dethloff.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherDAO {
    Teacher save(Teacher teacher);
    Teacher update(Teacher teacher);
    void delete(String id);
    Optional<Teacher> findById(String id);
    Optional<List<Teacher>> findAll();
    boolean existsById(String id);
    Teacher createProxy(String id);

}
