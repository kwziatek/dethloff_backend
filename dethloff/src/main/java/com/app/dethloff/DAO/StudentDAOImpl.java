package com.app.dethloff.DAO;

import com.app.dethloff.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
    public void save(Student theStudent) {
        entityManager.persist(theStudent);
    }

    @Override
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    public Optional<Student> findById(String theId) {
        Student student = entityManager.find(Student.class, theId);
        return Optional.ofNullable(student);

    }

    @Override
    public Optional<List<Student>> findAll() {
        List<Student> list = entityManager.createQuery("SELECT u from Student u", Student.class).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public void remove(Student theStudent) {
        entityManager.remove(theStudent);
    }
}
