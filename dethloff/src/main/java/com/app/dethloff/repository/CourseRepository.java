package com.app.dethloff.repository;

import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.SetOfCoursesEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, String> {
  List<CourseEntity> findAllBySetOfCourses_Id(String setOfCoursesId);

  List<CourseEntity> findAllBySetOfCourses(SetOfCoursesEntity setOfCourses);
}
