package com.app.dethloff.repository;

import com.app.dethloff.model.SetOfCoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetOfCoursesRepository extends JpaRepository<SetOfCoursesEntity, String> {
}
