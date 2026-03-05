package com.app.dethloff.dao;

import com.app.dethloff.model.TeacherEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeacherDAOImpl implements  TeacherDAO{

    private EntityManager entityManager;

    @Autowired
    public TeacherDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TeacherEntity save(TeacherEntity teacher) {
        entityManager.persist(teacher);
        return teacher;
    }

    @Override
    public TeacherEntity update(TeacherEntity teacher) {
        return entityManager.merge(teacher);
    }

    @Override
    public void delete(String id) {
        entityManager.remove(entityManager.find(TeacherEntity.class, id));
    }

    @Override
    public Optional<TeacherEntity> findById(String id) {
        return Optional.ofNullable(entityManager.find(TeacherEntity.class, id));
    }

    @Override
    public Optional<List<TeacherEntity>> findAll() {
        return Optional.ofNullable(entityManager.createQuery("SELECT u FROM TeacherEntity u", TeacherEntity.class).getResultList());
    }

    @Override
    public boolean existsById(String id) {
        Optional<TeacherEntity> teacher = findById(id);
        return teacher.isPresent();
    }

    @Override
    public TeacherEntity createProxy(String id) {
        return entityManager.getReference(TeacherEntity.class, id);
    }
}
