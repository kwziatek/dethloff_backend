package com.app.dethloff.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.StudentAlreadyEnrolledException;
import com.app.dethloff.exceptions.model.StudentNotEnrolledException;
import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.*;
import com.app.dethloff.repository.CourseRepository;
import com.app.dethloff.repository.LessonRepository;
import com.app.dethloff.repository.StudentRepository;
import com.app.dethloff.rest.DTO.CourseResponseDTO;
import com.app.dethloff.rest.mappers.CourseMapper;
import com.app.dethloff.service.impl.CourseServiceImpl;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplUTest {

  @Mock private CourseRepository courseRepository;

  @Mock private StudentRepository studentRepository;

  @Mock private LessonRepository lessonRepository;

  @InjectMocks private CourseServiceImpl courseService;

  @Mock private CourseMapper courseMapper;

  @Captor private ArgumentCaptor<CourseEntity> captor;

  private CourseEntity sampleCourse;
  private StudentEntity sampleStudent;
  private TeacherEntity sampleTeacher;
  private LessonEntity sampleOldestLesson;
  private LessonEntity sampleNewestLesson;
  private LessonTimeEntity sampleOldestLessonTime;
  private LessonTimeEntity sampleNewestLessonTime;

  @BeforeEach
  void setUp() {
    UUID studentId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();
    UUID teacherID = UUID.randomUUID();

    sampleStudent =
        StudentEntity.builder()
            .id(studentId.toString())
            .name("Karol")
            .surname("Piątkowski")
            .courses(new ArrayList<>())
            .build();
    sampleTeacher =
        TeacherEntity.builder()
            .id(teacherID.toString())
            .name("Michał")
            .surname("Żarowski")
            .courses(new ArrayList<>())
            .build();

    sampleCourse =
        CourseEntity.builder()
            .id(courseId.toString())
            .level(CourseLevel.A1)
            .description("NICE")
            .students(new ArrayList<>())
            .teacher(sampleTeacher)
            .startDate(null)
            .endDate(null)
            .build();

    sampleOldestLessonTime =
        LessonTimeEntity.builder()
            .id(UUID.randomUUID().toString())
            .lesson(sampleOldestLesson)
            .date(LocalDate.of(2006, 10, 30))
            .startTime(LocalTime.of(16, 30))
            .endTime(LocalTime.of(18, 0))
            .build();

    sampleOldestLesson =
        LessonEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Lekcja 1")
            .course(sampleCourse)
            .description("Wprowadzenie do zagadanień")
            .teacher(sampleTeacher)
            .lessonTime(sampleOldestLessonTime)
            .build();

    sampleNewestLessonTime =
        LessonTimeEntity.builder()
            .id(UUID.randomUUID().toString())
            .lesson(sampleNewestLesson)
            .date(LocalDate.of(2040, 10, 30))
            .startTime(LocalTime.of(16, 30))
            .endTime(LocalTime.of(18, 0))
            .build();

    sampleNewestLesson =
        LessonEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Lekcja 2")
            .course(sampleCourse)
            .description("Wprowadzenie do zagadanień c.d.")
            .teacher(sampleTeacher)
            .lessonTime(sampleNewestLessonTime)
            .build();
  }

  @Test
  void getCourse_shouldReturnDTO_whenIdExists() {
    // given
    when(courseRepository.findById(sampleCourse.getId()))
        .thenReturn(Optional.ofNullable(sampleCourse));

    when(courseMapper.entityToResponse(sampleCourse))
        .thenReturn(
            CourseResponseDTO.builder()
                .id(sampleCourse.getId())
                .name(sampleCourse.getName())
                .description(sampleCourse.getDescription())
                .level(sampleCourse.getLevel())
                .studentIds(null)
                .teacherId(null) // teacherMapper would need to be mocked
                .build());

    // when
    CourseResponseDTO courseResponseDTO = courseService.get(sampleCourse.getId());

    // then
    assertNotNull(courseResponseDTO);
    assertEquals(sampleCourse.getId(), courseResponseDTO.id());
  }

  @Test
  void getCourse_CourseNotFound_ThrowsException() {
    // given
    when(courseRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(CourseNotFoundException.class, () -> courseService.get("NON-EXISTENT"));

    // verify
    verify(courseRepository).findById("NON-EXISTENT");
  }

  @Test
  void createCourse_ShouldSaveAndReturnDTO() {}

  @Test
  void deleteCourseShouldInvokeDeleteOnRepository() {
    // given
    when(courseRepository.findById(sampleCourse.getId()))
        .thenReturn(Optional.ofNullable(sampleCourse));

    // when
    courseService.delete(sampleCourse.getId());

    // then
    verify(courseRepository).delete(sampleCourse);
  }

  @Test
  void deleteThrowsExceptionWhenCourseNotFound() {
    // given
    when(courseRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(CourseNotFoundException.class, () -> courseService.delete("NON-EXISTENT"));

    // verify
    // 1. Verify that delete() on courseRepository was NEVER called
    verify(courseRepository, never()).delete(any());
  }

  @Test
  void enrollStudentWorksCorrectly() {
    // given
    when(courseRepository.findById(sampleCourse.getId()))
        .thenReturn(Optional.ofNullable(sampleCourse));
    when(studentRepository.findById(sampleStudent.getId()))
        .thenReturn(Optional.ofNullable(sampleStudent));

    // when
    courseService.enrollStudent(sampleCourse.getId(), sampleStudent.getId());

    // then
    assertTrue(sampleCourse.getStudents().contains(sampleStudent));
    assertTrue(sampleStudent.getCourses().contains(sampleCourse));

    // verify
    verify(courseRepository).findById(sampleCourse.getId());
    verify(studentRepository).findById(sampleStudent.getId());
  }

  @Test
  void enrollStudentThrowsExceptionWhenCourseNotFound() {
    // given
    when(courseRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(
        CourseNotFoundException.class, () -> courseService.enrollStudent("NON-EXISTENT", any()));

    // verify studentDAO was never called because the course failed first
    verifyNoInteractions(studentRepository);
    verify(courseRepository).findById("NON-EXISTENT");
    verify(courseRepository, never()).save(any());
  }

  @Test
  void enrollStudentThrowsExceptionWhenStudentNotFound() {
    // given
    when(studentRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());
    // course fetching needs to be stubbed, it returns Optional.empty() otherwise which throws
    // CourseNotFoundException
    when(courseRepository.findById(sampleCourse.getId())).thenReturn(Optional.of(sampleCourse));

    // when && then
    assertThrows(
        StudentNotFoundException.class,
        () -> courseService.enrollStudent(sampleCourse.getId(), "NON-EXISTENT"));

    // verify
    // 1. Check if nothing was saved
    verify(courseRepository, never()).save(any());
    verify(studentRepository, never()).save(any());
  }

  @Test
  void enrollStudentThrowsExceptionWhenStudentIsAlreadyEnrolled() {
    // given
    when(courseRepository.findById(sampleCourse.getId())).thenReturn(Optional.of(sampleCourse));
    when(studentRepository.findById(sampleStudent.getId())).thenReturn(Optional.of(sampleStudent));
    // make sure the student is enrolled before enrolling them
    sampleCourse.addStudent(sampleStudent);

    // when && then
    assertThrows(
        StudentAlreadyEnrolledException.class,
        () -> courseService.enrollStudent(sampleCourse.getId(), sampleStudent.getId()));

    // verify
    // 1. Check if nothing was saved
    verify(courseRepository, never()).save(any());
    verify(studentRepository, never()).save(any());
  }

  @Test
  void unenrollStudentWorksCorrectly() {
    // given
    when(courseRepository.findById(sampleCourse.getId()))
        .thenReturn(Optional.ofNullable(sampleCourse));
    when(studentRepository.findById(sampleStudent.getId()))
        .thenReturn(Optional.ofNullable(sampleStudent));
    // student must be added to the course, otherwise unenrollStudent() throws exception as it
    // should
    sampleCourse.addStudent(sampleStudent);

    // when
    courseService.unenrollStudent(sampleCourse.getId(), sampleStudent.getId());

    // then
    assertFalse(sampleCourse.getStudents().contains(sampleStudent));
    assertFalse(sampleStudent.getCourses().contains(sampleCourse));

    // verify
    verify(courseRepository).findById(sampleCourse.getId());
    verify(studentRepository).findById(sampleStudent.getId());
  }

  @Test
  void unenrollStudentThrowsExceptionWhenCourseNotFound() {
    // given
    when(courseRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(
        CourseNotFoundException.class, () -> courseService.unenrollStudent("NON-EXISTENT", any()));

    // verify
    verifyNoInteractions(studentRepository);
    verify(courseRepository).findById("NON-EXISTENT");
    verify(courseRepository, never()).save(any());
  }

  @Test
  void unenrollStudentThrowsExceptionWhenStudentNotFound() {
    // given
    when(courseRepository.findById(sampleCourse.getId())).thenReturn(Optional.of(sampleCourse));
    when(studentRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(
        StudentNotFoundException.class,
        () -> courseService.unenrollStudent(sampleCourse.getId(), "NON-EXISTENT"));

    // verify
    // 1. Check if nothing was saved
    verify(courseRepository, never()).save(any());
    verify(studentRepository, never()).save(any());
  }

  @Test
  void unenrollStudentThrowsExceptionWhenStudentIsAlreadyEnrolled() {
    // given
    when(courseRepository.findById(sampleCourse.getId())).thenReturn(Optional.of(sampleCourse));
    when(studentRepository.findById(sampleStudent.getId())).thenReturn(Optional.of(sampleStudent));
    // make sure the student is unenrolled before unenrolling them
    sampleCourse.removeStudent(sampleStudent);

    // when && then
    assertThrows(
        StudentNotEnrolledException.class,
        () -> courseService.unenrollStudent(sampleCourse.getId(), sampleStudent.getId()));

    // verify
    // 1. Check if nothing was saved
    verify(courseRepository, never()).save(any());
    verify(studentRepository, never()).save(any());
  }

  @Test
  void updateCourseStartAndEndDateSetsWorksProperly() {
    // given
    when(courseRepository.findById(sampleCourse.getId())).thenReturn(Optional.of(sampleCourse));
    when(lessonRepository.findFirstByCourseIdOrderByLessonTimeDateAsc(sampleCourse.getId()))
        .thenReturn(Optional.of(sampleOldestLesson));
    when(lessonRepository.findFirstByCourseIdOrderByLessonTimeDateDesc(sampleCourse.getId()))
        .thenReturn(Optional.of(sampleNewestLesson));

    // when
    courseService.updateStartAndEndDateForCourse(sampleCourse.getId());

    // then
    // 1. verify that save has been called and save it to captor
    // 2. check if the saved value of isActive field is true
    // 3. check if the start date and end date map the oldest and newest lesson time date
    verify(courseRepository).save(captor.capture());
    CourseEntity savedCourse = captor.getValue();
    assertThat(savedCourse.isActive()).isTrue();
    assertThat(savedCourse.getStartDate()).isEqualTo(sampleOldestLessonTime.getDate());
    assertThat(savedCourse.getEndDate()).isEqualTo(sampleNewestLessonTime.getDate());
  }
}
