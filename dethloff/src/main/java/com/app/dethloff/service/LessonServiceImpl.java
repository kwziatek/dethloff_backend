package com.app.dethloff.service;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.LessonDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.LessonNotFoundException;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import com.app.dethloff.rest.mappers.LessonMapper;
import com.app.dethloff.model.LessonEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService{
    private final LessonMapper lessonMapper;
    private final LessonDAO lessonDAO;
    private final CourseDAO courseDAO;
    private final TeacherDAO teacherDAO;

    @Autowired
    public LessonServiceImpl(LessonMapper lessonMapper, LessonDAO lessonDAO, CourseDAO courseDAO, TeacherDAO teacherDAO) {
        this.lessonMapper = lessonMapper;
        this.lessonDAO = lessonDAO;
        this.courseDAO = courseDAO;
        this.teacherDAO = teacherDAO;
    }

    @Override
    public LessonResponseDTO get(String id) {
        LessonEntity lesson = lessonDAO.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("No lesson with such id - " + id));
        return lessonMapper.toLessonResponseDTO(lesson);
    }

    @Override
    public List<LessonResponseDTO> getAll() {
        return lessonMapper.toLessonResponseDTO(lessonDAO.findAll());
    }

    @Override
    public List<LessonResponseDTO> getAllFromCourse(String courseId) {
        return lessonMapper.toLessonResponseDTO(lessonDAO.findAllByCourseId(courseId));
    }

    @Override
    @Transactional
    public LessonResponseDTO create(LessonRequestDTO lessonDTO) {
        Optional.ofNullable(lessonDTO.courseId())
                .filter(courseDAO::existsById)
                .orElseThrow(() -> new CourseNotFoundException(("Course not found or ID is null")));

        Optional.ofNullable(lessonDTO.teacherId())
                .filter(teacherDAO::existsById)
                .orElseThrow(() -> new TeacherNotFoundException(("Teacher not found or ID is null")));

        LessonEntity lessonEntity = lessonMapper.toEntity(lessonDTO);
        return lessonMapper.toLessonResponseDTO(lessonDAO.save(lessonEntity));
    }

    @Override
    @Transactional
    public LessonResponseDTO update(LessonRequestDTO lessonDTO) {
        LessonEntity existingLesson = lessonDAO.findById(lessonDTO.id())
                .orElseThrow(() -> new LessonNotFoundException("No lesson with such id - " + lessonDTO.id()));

        lessonMapper.updateEntityFromRequest(lessonDTO, existingLesson);

        return lessonMapper.toLessonResponseDTO(lessonDAO.update(existingLesson));
    }

    @Override
    @Transactional
    public void delete(String lessonId) {
        LessonEntity lessonEntity = lessonDAO.findById(lessonId).orElseThrow(LessonNotFoundException::new);
        lessonDAO.remove(lessonEntity);
    }
}
