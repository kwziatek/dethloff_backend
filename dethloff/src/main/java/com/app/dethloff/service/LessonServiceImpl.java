package com.app.dethloff.service;

import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.LessonNotFoundException;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.repository.CourseRepository;
import com.app.dethloff.repository.LessonRepository;
import com.app.dethloff.repository.TeacherRepository;
import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import com.app.dethloff.rest.mappers.LessonMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService{
    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepo;
    private final CourseRepository courseRepo;
    private final TeacherRepository teacherRepo;

    @Autowired
    public LessonServiceImpl(LessonMapper lessonMapper, LessonRepository lessonRepo, CourseRepository courseRepo, TeacherRepository teacherRepo) {
        this.lessonMapper = lessonMapper;
        this.lessonRepo = lessonRepo;
        this.courseRepo = courseRepo;
        this.teacherRepo = teacherRepo;
    }

    @Override
    public LessonResponseDTO get(String id) {
        LessonEntity lesson = lessonRepo.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("No lesson with such id - " + id));
        return lessonMapper.entityToResponse(lesson);
    }

    @Override
    public List<LessonResponseDTO> getAll() {
        return lessonRepo.findAll().stream().map(lessonMapper::entityToResponse).toList();
    }

    @Override
    public List<LessonResponseDTO> getAllFromCourse(String courseId) {
        return lessonRepo.findAllByCourse_Id(courseId).stream().map(lessonMapper::entityToResponse).toList();
    }

    @Override
    @Transactional
    public LessonResponseDTO create(LessonRequestDTO lessonDTO) {
        Optional.ofNullable(lessonDTO.courseId())
                .filter(courseRepo::existsById)
                .orElseThrow(() -> new CourseNotFoundException(("Course not found or ID is null")));

        Optional.ofNullable(lessonDTO.teacherId())
                .filter(teacherRepo::existsById)
                .orElseThrow(() -> new TeacherNotFoundException(("Teacher not found or ID is null")));

        LessonEntity lessonEntity = lessonMapper.requestToEntity(lessonDTO);
        return lessonMapper.entityToResponse(lessonRepo.save(lessonEntity));
    }

    @Override
    @Transactional
    public LessonResponseDTO update(LessonRequestDTO lessonDTO) {
        LessonEntity existingLesson = lessonRepo.findById(lessonDTO.id())
                .orElseThrow(() -> new LessonNotFoundException("No lesson with such id - " + lessonDTO.id()));

        lessonMapper.updateEntityFromRequest(lessonDTO, existingLesson);

        return lessonMapper.entityToResponse(lessonRepo.save(existingLesson));
    }

    @Override
    @Transactional
    public void delete(String lessonId) {
        LessonEntity lessonEntity = lessonRepo.findById(lessonId).orElseThrow(LessonNotFoundException::new);
        lessonRepo.delete(lessonEntity);
    }
}
