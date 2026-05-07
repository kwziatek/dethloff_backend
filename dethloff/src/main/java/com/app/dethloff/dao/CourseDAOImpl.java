package com.app.dethloff.dao;

import com.app.dethloff.model.CourseEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAOImpl implements CourseDAO{


    private final EntityManager entityManager;

    @Autowired
    public CourseDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CourseEntity save(CourseEntity course) {
         entityManager.persist(course);
         return course;
    }

    @Override
    public CourseEntity update(CourseEntity course) {
        return entityManager.merge(course);
    }

    @Override
    public Optional<CourseEntity> findById(String id) {
        CourseEntity course = entityManager.find(CourseEntity.class, id);
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<List<CourseEntity>> findAll() {
        List<CourseEntity> list = entityManager.createQuery("SELECT u from CourseEntity u", CourseEntity.class).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public void remove(CourseEntity course) {
        entityManager.remove(course);
    }

    @Override
    public Optional<List<CourseEntity>> findAllBySetId(String setOfCoursesId) {
        List<CourseEntity> list = entityManager.createQuery("SELECT u from CourseEntity u where u.setOfCourses.id = :id", CourseEntity.class)
                .setParameter("id", setOfCoursesId)
                .getResultList();
        return Optional.ofNullable(list);
    }
}
