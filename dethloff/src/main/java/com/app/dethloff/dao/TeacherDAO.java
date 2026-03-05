package com.app.dethloff.dao;

import com.app.dethloff.model.TeacherEntity;

import java.util.List;
import java.util.Optional;

public interface TeacherDAO {
    TeacherEntity save(TeacherEntity teacher);
    TeacherEntity update(TeacherEntity teacher);
    void delete(String id);
    Optional<TeacherEntity> findById(String id);
    Optional<List<TeacherEntity>> findAll();
    boolean existsById(String id);
    TeacherEntity createProxy(String id);

}
