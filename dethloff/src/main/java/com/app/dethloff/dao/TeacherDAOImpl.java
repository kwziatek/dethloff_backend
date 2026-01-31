package com.app.dethloff.dao;

import com.app.dethloff.model.Teacher;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
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
    public Teacher save(Teacher teacher) {
        entityManager.persist(teacher);
        return teacher;
    }

    @Override
    public Teacher update(Teacher teacher) {
        return entityManager.merge(teacher);
    }

    @Override
    public void delete(String id) {
        entityManager.remove(entityManager.find(Teacher.class, id));
    }

    @Override
    public Optional<Teacher> findById(String id) {
        return Optional.ofNullable(entityManager.find(Teacher.class, id));
    }

    @Override
    public Optional<List<Teacher>> findAll() {
        return Optional.ofNullable(entityManager.createQuery("SELECT u FROM Teacher u", Teacher.class).getResultList());
    }

    @Override
    public boolean existsById(String id) {
        Optional<Teacher> teacher = findById(id);
        return teacher.isPresent();
    }

    @Override
    public Teacher createProxy(String id) {
        return entityManager.getReference(Teacher.class, id);
    }
}
