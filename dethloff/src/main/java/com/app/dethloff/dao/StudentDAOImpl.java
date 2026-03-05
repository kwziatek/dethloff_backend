package com.app.dethloff.dao;

import com.app.dethloff.model.StudentEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAOImpl implements StudentDAO{

    private EntityManager entityManager;

    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public StudentEntity save(StudentEntity theStudent) {
        entityManager.persist(theStudent);
        return theStudent;
    }

    @Override
    public StudentEntity update(StudentEntity student) {
        return entityManager.merge(student);
    }

    @Override
    public Optional<StudentEntity> findById(String theId) {
        StudentEntity student = entityManager.find(StudentEntity.class, theId);
        return Optional.ofNullable(student);

    }

    @Override
    public Optional<List<StudentEntity>> findAll() {
        List<StudentEntity> list = entityManager.createQuery("SELECT u from StudentEntity u", StudentEntity.class).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public void remove(StudentEntity theStudent) {
        entityManager.remove(theStudent);
    }
}
