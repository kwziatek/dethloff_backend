package com.app.dethloff.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.StudentAlreadyEnrolledException;
import com.app.dethloff.exceptions.model.StudentNotEnrolledException;
import com.app.dethloff.exceptions.model.StudentNotFoundException;
import com.app.dethloff.model.*;
import com.app.dethloff.repository.*;
import com.app.dethloff.rest.DTO.CourseRequestDTO;
import com.app.dethloff.rest.DTO.CourseResponseDTO;
import com.app.dethloff.rest.mappers.CourseMapper;
import com.app.dethloff.service.impl.CourseServiceImpl;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

  @Mock private TeacherRepository teacherRepository;

  @Mock private SetOfCoursesRepository setOfCoursesRepository;

  @InjectMocks private CourseServiceImpl courseService;

  @Mock private CourseMapper courseMapper;

  @Captor private ArgumentCaptor<CourseEntity> courseCaptor;

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
  void createCourse_ShouldSaveAndReturnDTO() {
    // given
    CourseRequestDTO request =
        CourseRequestDTO.builder()
            .teacherId(sampleTeacher.getId())
            .setOfCoursesId(UUID.randomUUID().toString())
            .studentIds(List.of(sampleStudent.getId()))
            .description("New Course Description")
            .level(CourseLevel.B1)
            .build();
    CourseResponseDTO expectedResponse =
        CourseResponseDTO.builder()
            .id(UUID.randomUUID().toString())
            .description("New Course Description")
            .level(CourseLevel.B1)
            .build();

    sampleCourse.setActive(true);

    when(teacherRepository.findById(request.teacherId())).thenReturn(Optional.of(sampleTeacher));
    when(setOfCoursesRepository.findById(request.setOfCoursesId()))
        .thenReturn(Optional.of(new SetOfCoursesEntity()));
    when(studentRepository.findAllById(request.studentIds())).thenReturn(List.of(sampleStudent));
    when(courseMapper.requestToEntity(request)).thenReturn(sampleCourse);
    when(courseMapper.entityToResponse(sampleCourse)).thenReturn(expectedResponse);

    // when
    CourseResponseDTO actualResponse = courseService.create(request);

    // then
    verify(courseRepository).save(sampleCourse);
    assertThat(actualResponse).isEqualTo(expectedResponse);
    assertThat(sampleTeacher.getIsActive()).isTrue();
  }

  @Test
  void updateCourse_ShouldUpdateAndReturnDTO() {
    // given
    CourseRequestDTO request =
        CourseRequestDTO.builder()
            .id(sampleCourse.getId())
            .description("Updated Description")
            .level(CourseLevel.B2)
            .build();
    CourseResponseDTO expectedResponse =
        CourseResponseDTO.builder()
            .id(sampleCourse.getId())
            .description("Updated Description")
            .level(CourseLevel.B2)
            .build();

    when(courseRepository.findById(request.id())).thenReturn(Optional.of(sampleCourse));
    when(courseRepository.save(sampleCourse)).thenReturn(sampleCourse);
    when(courseMapper.entityToResponse(sampleCourse)).thenReturn(expectedResponse);

    // when
    CourseResponseDTO actualResponse = courseService.update(request);

    // then
    verify(courseMapper).updateEntityFromRequest(request, sampleCourse);
    verify(courseRepository).save(sampleCourse);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void updateCourse_ThrowsExceptionWhenCourseNotFound() {
    // given
    CourseRequestDTO request = CourseRequestDTO.builder().id("NON-EXISTENT").build();

    when(courseRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(CourseNotFoundException.class, () -> courseService.update(request));

    // verify
    verify(courseRepository, never()).save(any());
    verifyNoInteractions(courseMapper);
  }

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

    // let's set the course to be active and the student not to check if the student is set as
    // active
    sampleCourse.setActive(true);
    sampleStudent.setIsActive(false);

    // when
    courseService.enrollStudent(sampleCourse.getId(), sampleStudent.getId());

    // then
    // 1. Check that student references course and the other way around
    assertTrue(sampleCourse.getStudents().contains(sampleStudent));
    assertTrue(sampleStudent.getCourses().contains(sampleCourse));

    // 2. Check that student status is changed to active
    assertThat(sampleStudent.getIsActive()).isTrue();

    // 3. verify that findById() was called on course and student (entities in managed(?) state)
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

    // this also adds reference for the student to the course, check out the method
    sampleCourse.addStudent(sampleStudent);

    // when
    courseService.unenrollStudent(sampleCourse.getId(), sampleStudent.getId());

    // then
    assertFalse(sampleCourse.getStudents().contains(sampleStudent));
    assertFalse(sampleStudent.getCourses().contains(sampleCourse));

    assertThat(sampleStudent.getIsActive()).isFalse();

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
    verify(courseRepository).save(courseCaptor.capture());
    CourseEntity savedCourse = courseCaptor.getValue();
    assertThat(savedCourse.isActive()).isTrue();
    assertThat(savedCourse.getStartDate()).isEqualTo(sampleOldestLessonTime.getDate());
    assertThat(savedCourse.getEndDate()).isEqualTo(sampleNewestLessonTime.getDate());
  }
}
