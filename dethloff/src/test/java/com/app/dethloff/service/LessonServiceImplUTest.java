package com.app.dethloff.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.LessonNotFoundException;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.*;
import com.app.dethloff.repository.CourseRepository;
import com.app.dethloff.repository.LessonRepository;
import com.app.dethloff.repository.TeacherRepository;
import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import com.app.dethloff.rest.mappers.LessonMapper;
import com.app.dethloff.service.impl.LessonServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LessonServiceImplUTest {

  @Mock private LessonRepository lessonRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private TeacherRepository teacherRepository;
  @Mock private LessonMapper lessonMapper;

  @InjectMocks private LessonServiceImpl lessonService;

  private LessonEntity sampleLesson;
  private CourseEntity sampleCourse;
  private TeacherEntity sampleTeacher;

  @BeforeEach
  void setUp() {
    sampleCourse =
        CourseEntity.builder()
            .id(UUID.randomUUID().toString())
            .description("Sample Course")
            .build();
    sampleTeacher =
        TeacherEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("John")
            .surname("Doe")
            .build();
    sampleLesson =
        LessonEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Sample Lesson")
            .course(sampleCourse)
            .teacher(sampleTeacher)
            .build();
  }

  @Test
  void create_ShouldSaveAndReturnDTO() {
    // given
    LessonRequestDTO request =
        LessonRequestDTO.builder()
            .courseId(sampleCourse.getId())
            .teacherId(sampleTeacher.getId())
            .name("New Lesson")
            .build();
    LessonResponseDTO expectedResponse =
        LessonResponseDTO.builder().id(sampleLesson.getId()).name("New Lesson").build();

    when(courseRepository.findById(request.courseId())).thenReturn(Optional.of(sampleCourse));
    when(teacherRepository.findById(request.teacherId())).thenReturn(Optional.of(sampleTeacher));
    when(lessonMapper.requestToEntity(request)).thenReturn(sampleLesson);
    when(lessonMapper.entityToResponse(sampleLesson)).thenReturn(expectedResponse);

    // when
    LessonResponseDTO actualResponse = lessonService.create(request);

    // then
    verify(lessonRepository).save(sampleLesson);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void create_ThrowsExceptionWhenCourseNotFound() {
    // given
    LessonRequestDTO request =
        LessonRequestDTO.builder()
            .courseId("NON-EXISTENT")
            .teacherId(sampleTeacher.getId())
            .build();

    when(courseRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(CourseNotFoundException.class, () -> lessonService.create(request));
    verifyNoInteractions(teacherRepository, lessonRepository);
  }

  @Test
  void create_ThrowsExceptionWhenTeacherNotFound() {
    // given
    LessonRequestDTO request =
        LessonRequestDTO.builder().courseId(sampleCourse.getId()).teacherId("NON-EXISTENT").build();

    when(courseRepository.findById(sampleCourse.getId())).thenReturn(Optional.of(sampleCourse));
    when(teacherRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(TeacherNotFoundException.class, () -> lessonService.create(request));
    verifyNoInteractions(lessonRepository);
  }

  @Test
  void get_ShouldReturnDTO_WhenIdExists() {
    // given
    LessonResponseDTO expectedResponse =
        LessonResponseDTO.builder().id(sampleLesson.getId()).name(sampleLesson.getName()).build();
    when(lessonRepository.findById(sampleLesson.getId())).thenReturn(Optional.of(sampleLesson));
    when(lessonMapper.entityToResponse(sampleLesson)).thenReturn(expectedResponse);

    // when
    LessonResponseDTO actualResponse = lessonService.get(sampleLesson.getId());

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);
    verify(lessonRepository).findById(sampleLesson.getId());
  }

  @Test
  void get_ThrowsExceptionWhenLessonNotFound() {
    // given
    String id = "NON-EXISTENT";
    when(lessonRepository.findById(id)).thenReturn(Optional.empty());

    // when && then
    assertThrows(LessonNotFoundException.class, () -> lessonService.get(id));

    verify(lessonRepository).findById("NON-EXISTENT");
    verifyNoInteractions(lessonMapper);
  }

  @Test
  void getAll_ShouldReturnAllLessons() {
    // given
    List<LessonEntity> lessons = List.of(sampleLesson);
    LessonResponseDTO response = LessonResponseDTO.builder().id(sampleLesson.getId()).build();
    when(lessonRepository.findAll()).thenReturn(lessons);
    when(lessonMapper.entityToResponse(sampleLesson)).thenReturn(response);

    // when
    List<LessonResponseDTO> actualResponse = lessonService.getAll();

    // then
    assertThat(actualResponse).containsExactly(response);
    verify(lessonRepository).findAll();
  }

  @Test
  void getAllFromCourse_ShouldReturnLessonsForCourse() {
    // given
    List<LessonEntity> lessons = List.of(sampleLesson);
    LessonResponseDTO response = LessonResponseDTO.builder().id(sampleLesson.getId()).build();
    when(lessonRepository.findAllByCourse_Id(sampleCourse.getId())).thenReturn(lessons);
    when(lessonMapper.entityToResponse(sampleLesson)).thenReturn(response);

    // when
    List<LessonResponseDTO> actualResponse = lessonService.getAllFromCourse(sampleCourse.getId());

    // then
    assertThat(actualResponse).containsExactly(response);
    verify(lessonRepository).findAllByCourse_Id(sampleCourse.getId());
  }

  @Test
  void update_ShouldUpdateAndReturnDTO() {
    // given
    LessonRequestDTO request =
        LessonRequestDTO.builder().id(sampleLesson.getId()).name("Updated Lesson Name").build();
    LessonResponseDTO expectedResponse =
        LessonResponseDTO.builder().id(sampleLesson.getId()).name("Updated Lesson Name").build();

    when(lessonRepository.findById(request.id())).thenReturn(Optional.of(sampleLesson));
    when(lessonRepository.save(sampleLesson)).thenReturn(sampleLesson);
    when(lessonMapper.entityToResponse(sampleLesson)).thenReturn(expectedResponse);

    // when
    LessonResponseDTO actualResponse = lessonService.update(request);

    // then
    verify(lessonMapper).updateEntityFromRequest(request, sampleLesson);
    verify(lessonRepository).save(sampleLesson);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void update_ThrowsExceptionWhenLessonNotFound() {
    // given
    LessonRequestDTO request = LessonRequestDTO.builder().id("NON-EXISTENT").build();
    when(lessonRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

    // when && then
    assertThrows(LessonNotFoundException.class, () -> lessonService.update(request));
    verify(lessonRepository, never()).save(any());
  }

  @Test
  void delete_ShouldRemoveLesson() {
    // given
    when(lessonRepository.findById(sampleLesson.getId())).thenReturn(Optional.of(sampleLesson));

    // when
    lessonService.delete(sampleLesson.getId());

    // then
    verify(lessonRepository).delete(sampleLesson);
  }

  @Test
  void delete_ThrowsExceptionWhenLessonNotFound() {
    // given
    String id = "NON-EXISTENT";
    when(lessonRepository.findById(id)).thenReturn(Optional.empty());

    // when && then
    assertThrows(LessonNotFoundException.class, () -> lessonService.delete(id));
    verify(lessonRepository, never()).delete(any());
  }
}
