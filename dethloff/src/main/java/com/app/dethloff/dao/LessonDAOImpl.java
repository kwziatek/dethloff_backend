package com.app.dethloff.dao;

import com.app.dethloff.model.LessonEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LessonDAOImpl implements LessonDAO{
    private EntityManager entityManager;

    @Autowired
    LessonDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public LessonEntity save(LessonEntity lesson) {
        entityManager.persist(lesson);
        return lesson;
    }

    @Override
    public LessonEntity update(LessonEntity lesson) {
        return entityManager.merge(lesson);
    }

    @Override
    public Optional<LessonEntity> findById(String id) {
        LessonEntity lesson = entityManager.find(LessonEntity.class, id);
        return Optional.ofNullable(lesson);
    }

    @Override
    public Optional<List<LessonEntity>> findAll() {
        return Optional.empty();
    }

    @Override
    public void remove(LessonEntity lesson) {
        entityManager.remove(lesson);
    }

    @Override
    public LessonEntity createProxy(String id) {
        return entityManager.getReference(LessonEntity.class, id);
    }
}
