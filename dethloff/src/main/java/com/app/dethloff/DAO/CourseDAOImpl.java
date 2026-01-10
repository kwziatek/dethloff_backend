package com.app.dethloff.DAO;

import com.app.dethloff.model.Course;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAOImpl implements CourseDAO{


    private EntityManager entityManager;

    @Autowired
    public CourseDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Course course) {
        System.out.println(course == null);
        entityManager.persist(course);
    }

    @Override
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Optional<Course> findById(String id) {
        Course course = entityManager.find(Course.class, id);
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<List<Course>> findAll() {
        List<Course> list = entityManager.createQuery("SELECT u from Course u", Course.class).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public void remove(Course course) {
        entityManager.remove(course);
    }
}
