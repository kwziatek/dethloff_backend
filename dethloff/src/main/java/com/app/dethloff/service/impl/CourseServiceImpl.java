package com.app.dethloff.service.impl;

import com.app.dethloff.exceptions.model.*;
import com.app.dethloff.model.*;
import com.app.dethloff.repository.*;
import com.app.dethloff.rest.DTO.CourseRequestDTO;
import com.app.dethloff.rest.DTO.CourseResponseDTO;
import com.app.dethloff.rest.DTO.SetOfCoursesResponseDTO;
import com.app.dethloff.rest.mappers.CourseMapper;
import com.app.dethloff.rest.mappers.SetOfCoursesMapper;
import com.app.dethloff.service.inte.CourseService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepo;
  private final StudentRepository studentRepo;
  private final TeacherRepository teacherRepo;
  private final LessonRepository lessonRepo;
  private final SetOfCoursesRepository setOfCoursesRepo;
  private final CourseMapper courseMapper;
  private final SetOfCoursesMapper setOfCoursesMapper;

  @Autowired
  public CourseServiceImpl(
      CourseRepository courseRepo,
      StudentRepository studentRepo,
      TeacherRepository teacherRepo,
      LessonRepository lessonRepo,
      CourseMapper courseMapper,
      SetOfCoursesRepository setOfCoursesRepo,
      SetOfCoursesMapper setOfCoursesMapper) {
    this.courseRepo = courseRepo;
    this.studentRepo = studentRepo;
    this.teacherRepo = teacherRepo;
    this.lessonRepo = lessonRepo;
    this.courseMapper = courseMapper;
    this.setOfCoursesRepo = setOfCoursesRepo;
    this.setOfCoursesMapper = setOfCoursesMapper;
  }

  @Override
  @Transactional
  public CourseResponseDTO create(CourseRequestDTO courseRequestDTO) {
    TeacherEntity teacher =
        teacherRepo
            .findById(courseRequestDTO.teacherId())
            .orElseThrow(TeacherNotFoundException::new);

    SetOfCoursesEntity setOfCourses =
        setOfCoursesRepo
            .findById(courseRequestDTO.setOfCoursesId())
            .orElseThrow(SetOfCoursesNotFoundException::new);

    List<StudentEntity> students = studentRepo.findAllById(courseRequestDTO.studentIds());

    CourseEntity course = courseMapper.requestToEntity(courseRequestDTO);
    course.setTeacher(teacher);
    if (course.isActive()) {
      teacher.setIsActive(true);
    }
    course.setSetOfCourses(setOfCourses);
    course.setStudents(students);
    courseRepo.save(course);
    return courseMapper.entityToResponse(course);
  }

  @Override
  public CourseResponseDTO get(String id) {
    CourseEntity course = courseRepo.findById(id).orElseThrow(CourseNotFoundException::new);
    return courseMapper.entityToResponse(course);
  }

  @Override
  public List<CourseResponseDTO> getAll() {
    return courseRepo.findAll().stream().map(courseMapper::entityToResponse).toList();
  }

  @Override
  public List<CourseResponseDTO> getAllFromParticularSet(String setOfCoursesId) {
    List<CourseEntity> courses = courseRepo.findAllBySetOfCourses_Id(setOfCoursesId);
    return courses.stream().map(courseMapper::entityToResponse).toList();
  }

  @Override
  public List<SetOfCoursesResponseDTO> getAllSetsOfCourses() {
    List<SetOfCoursesEntity> list = setOfCoursesRepo.findAll();
    return list.stream().map(setOfCoursesMapper::entityToResponse).toList();
  }

  @Transactional
  @Override
  public CourseResponseDTO update(CourseRequestDTO courseRequestDTO) {
    CourseEntity existingCourse =
        courseRepo.findById(courseRequestDTO.id()).orElseThrow(CourseNotFoundException::new);

    courseMapper.updateEntityFromRequest(courseRequestDTO, existingCourse);
    CourseEntity updatedCourse = courseRepo.save(existingCourse);
    return courseMapper.entityToResponse(updatedCourse);
  }

  @Transactional
  @Override
  public void delete(String id) {
    CourseEntity course = courseRepo.findById(id).orElseThrow(CourseNotFoundException::new);
    courseRepo.delete(course);
  }

  @Override
  @Transactional
  public void enrollStudent(String courseId, String studentId) {
    CourseEntity course = courseRepo.findById(courseId).orElseThrow(CourseNotFoundException::new);
    StudentEntity student =
        studentRepo.findById(studentId).orElseThrow(StudentNotFoundException::new);
    if (course.getStudents().contains(student)) {
      throw new StudentAlreadyEnrolledException();
    } else {
      course.addStudent(student);
      if (course.isActive()) {
        student.setIsActive(true);
      }
    }
  }

  @Override
  @Transactional
  public void unenrollStudent(String courseId, String studentId) {
    CourseEntity course = courseRepo.findById(courseId).orElseThrow(CourseNotFoundException::new);
    StudentEntity student =
        studentRepo.findById(studentId).orElseThrow(StudentNotFoundException::new);
    if (!course.getStudents().contains(student)) {
      throw new StudentNotEnrolledException();
    } else {
      course.removeStudent(student);
      if (student.getCourses().stream().noneMatch(CourseEntity::isActive)) {
        student.setIsActive(false);
      }
    }
  }

  @Transactional
  @Override
  public void updateStartAndEndDateForCourse(String id) {
    CourseEntity course = courseRepo.findById(id).orElseThrow(StudentNotFoundException::new);

    LessonEntity oldestLesson =
        lessonRepo.findFirstByCourseIdOrderByLessonTimeDateAsc(course.getId()).orElse(null);
    LessonEntity newestLesson =
        lessonRepo.findFirstByCourseIdOrderByLessonTimeDateDesc(course.getId()).orElse(null);

    boolean shouldBeSaved = false;

    if (oldestLesson != null
        && !oldestLesson.getLessonTime().getDate().equals(course.getStartDate())) {
      course.setStartDate(oldestLesson.getLessonTime().getDate());
      shouldBeSaved = true;
    }

    if (newestLesson != null
        && !newestLesson.getLessonTime().getDate().equals(course.getEndDate())) {
      course.setEndDate(newestLesson.getLessonTime().getDate());
      shouldBeSaved = true;
    }

    // no need to mark shouldBeSaved, if startDate or endDate don't change, active status won't
    // either
    if (course.getStartDate() != null && course.getEndDate() != null) {
      // check if the current date is not before the course start date and not after the course end
      // date
      course.setActive(
          !LocalDate.now().isBefore(course.getStartDate())
              && !LocalDate.now().isAfter(course.getEndDate()));
    } else {
      course.setActive(false);
    }

    if (shouldBeSaved) {
      courseRepo.save(course);
    }
  }
}
