package com.app.dethloff.service;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.LessonDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.exceptions.model.CourseNotFoundException;
import com.app.dethloff.exceptions.model.LessonNotFoundException;
import com.app.dethloff.exceptions.model.TeacherNotFoundException;
import com.app.dethloff.model.DTO.BasicLessonDTO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;
import com.app.dethloff.model.DTO.mappers.LessonMapper;
import com.app.dethloff.model.LessonEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService{
    LessonMapper lessonMapper;
    LessonDAO lessonDAO;
    CourseDAO courseDAO;
    TeacherDAO teacherDAO;

    @Autowired
    LessonServiceImpl(LessonMapper lessonMapper, LessonDAO lessonDAO, CourseDAO courseDAO, TeacherDAO teacherDAO) {
        this.lessonMapper = lessonMapper;
        this.lessonDAO = lessonDAO;
        this.courseDAO = courseDAO;
        this.teacherDAO = teacherDAO;
    }

    @Override
    public DetailedLessonDTO get(String id) {
        Optional<LessonEntity> lessonEntity = lessonDAO.findById(id);
        DetailedLessonDTO detailedLesson = null;
        if (lessonEntity.isPresent()) {
            detailedLesson = lessonMapper.toDetailedDTO(lessonEntity.get());
        }
        return detailedLesson;
    }

    @Override
    public List<DetailedLessonDTO> getAll() {
        return lessonMapper.toDetailedDTO(lessonDAO.findAll());
    }

    @Override
    public List<DetailedLessonDTO> getAllFromCourse(String courseId) {
        return lessonMapper.toDetailedDTO(lessonDAO.findAllByCourseId(courseId));
    }

    @Override
    @Transactional
    public DetailedLessonDTO create(BasicLessonDTO lessonDTO) {
        Optional.ofNullable(lessonDTO.courseId())
                .filter(courseDAO::existsById)
                .orElseThrow(() -> new CourseNotFoundException(("Course not found or ID is null")));

        Optional.ofNullable(lessonDTO.teacherId())
                .filter(teacherDAO::existsById)
                .orElseThrow(() -> new TeacherNotFoundException(("Teacher not found or ID is null")));

        LessonEntity lessonEntity = lessonMapper.toEntity(lessonDTO);
        return lessonMapper.toDetailedDTO(lessonDAO.save(lessonEntity));
    }

    @Override
    @Transactional
    public void delete(String lessonId) {
        LessonEntity lessonEntity = lessonDAO.findById(lessonId).orElseThrow(LessonNotFoundException::new);
        lessonDAO.remove(lessonEntity);
    }
}
