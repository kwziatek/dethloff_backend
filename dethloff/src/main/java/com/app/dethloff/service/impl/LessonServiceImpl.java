package com.app.dethloff.service.impl;

import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.LessonNotFoundException;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.model.TeacherEntity;
import com.app.dethloff.repository.CourseRepository;
import com.app.dethloff.repository.LessonRepository;
import com.app.dethloff.repository.TeacherRepository;
import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import com.app.dethloff.rest.mappers.LessonMapper;
import com.app.dethloff.service.inte.LessonService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {
  private final LessonMapper lessonMapper;
  private final LessonRepository lessonRepo;
  private final CourseRepository courseRepo;
  private final TeacherRepository teacherRepo;

  @Autowired
  public LessonServiceImpl(
      LessonMapper lessonMapper,
      LessonRepository lessonRepo,
      CourseRepository courseRepo,
      TeacherRepository teacherRepo) {
    this.lessonMapper = lessonMapper;
    this.lessonRepo = lessonRepo;
    this.courseRepo = courseRepo;
    this.teacherRepo = teacherRepo;
  }

  @Override
  @Transactional
  public LessonResponseDTO create(LessonRequestDTO lessonDTO) {
    CourseEntity course =
        courseRepo.findById(lessonDTO.courseId()).orElseThrow(CourseNotFoundException::new);

    TeacherEntity teacher =
        teacherRepo.findById(lessonDTO.teacherId()).orElseThrow(TeacherNotFoundException::new);

    LessonEntity lesson = lessonMapper.requestToEntity(lessonDTO);
    lesson.setCourse(course);
    lesson.setTeacher(teacher);
    lessonRepo.save(lesson);
    return lessonMapper.entityToResponse(lesson);
  }

  @Override
  public LessonResponseDTO get(String id) {
    LessonEntity lesson = lessonRepo.findById(id).orElseThrow(LessonNotFoundException::new);
    return lessonMapper.entityToResponse(lesson);
  }

  @Override
  public List<LessonResponseDTO> getAll() {
    return lessonRepo.findAll().stream().map(lessonMapper::entityToResponse).toList();
  }

  @Override
  public List<LessonResponseDTO> getAllFromCourse(String courseId) {
    return lessonRepo.findAllByCourse_Id(courseId).stream()
        .map(lessonMapper::entityToResponse)
        .toList();
  }

  @Override
  @Transactional
  public LessonResponseDTO update(LessonRequestDTO lessonDTO) {
    LessonEntity existingLesson =
        lessonRepo.findById(lessonDTO.id()).orElseThrow(LessonNotFoundException::new);

    lessonMapper.updateEntityFromRequest(lessonDTO, existingLesson);

    return lessonMapper.entityToResponse(lessonRepo.save(existingLesson));
  }

  @Override
  @Transactional
  public void delete(String lessonId) {
    LessonEntity lesson = lessonRepo.findById(lessonId).orElseThrow(LessonNotFoundException::new);
    lessonRepo.delete(lesson);
  }
}
