package com.app.dethloff.repository;

import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.SetOfCoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, String> {
    List<CourseEntity> findAllBySetOfCourses_Id(String setOfCoursesId);

    List<CourseEntity> findAllBySetOfCourses(SetOfCoursesEntity setOfCourses);
}
