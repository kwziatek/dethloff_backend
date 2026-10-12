package com.app.dethloff.dao;

import com.app.dethloff.model.LessonTimeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class LessonTimeDAOImpl implements LessonTimeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LessonTimeEntity save(LessonTimeEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public LessonTimeEntity update(LessonTimeEntity entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<LessonTimeEntity> findById(String id) {
        return Optional.ofNullable(entityManager.find(LessonTimeEntity.class, id));
    }

    @Override
    public Optional<LessonTimeEntity> findByLessonId(String lessonId) {
        return entityManager.createQuery(
            "SELECT lt FROM LessonTimeEntity lt WHERE lt.lesson.id = :lessonId", LessonTimeEntity.class)
            .setParameter("lessonId", lessonId)
            .getResultStream()
            .findFirst();
    }

    @Override
    public void remove(LessonTimeEntity entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
