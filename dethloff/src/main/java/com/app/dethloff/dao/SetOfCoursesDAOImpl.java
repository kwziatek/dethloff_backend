package com.app.dethloff.dao;

import com.app.dethloff.model.SetOfCoursesEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SetOfCoursesDAOImpl implements SetOfCoursesDAO{

    EntityManager entityManager;

    @Autowired
    public SetOfCoursesDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<List<SetOfCoursesEntity>> findAll() {
        List<SetOfCoursesEntity> list = entityManager.createQuery("SELECT u from SetOfCoursesEntity u", SetOfCoursesEntity.class).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<SetOfCoursesEntity> findById(String id) {
        return Optional.ofNullable(entityManager.find(SetOfCoursesEntity.class, id));
    }

    @Override
    public boolean existsById(String id) {
        Optional<SetOfCoursesEntity> setOfCourses = findById(id);
        return setOfCourses.isPresent();
    }

    @Override
    public SetOfCoursesEntity createProxy(String id) {
        return entityManager.getReference(SetOfCoursesEntity.class, id);
    }
}
