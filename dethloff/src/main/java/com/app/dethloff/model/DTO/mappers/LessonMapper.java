package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.DTO.BasicLessonDTO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;
import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.model.TeacherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {
    CourseDAO courseDAO;
    TeacherDAO teacherDAO;
    CourseMapper courseMapper;
    TeacherMapper teacherMapper;

    @Autowired
    LessonMapper(CourseDAO courseDAO, TeacherDAO teacherDAO, CourseMapper courseMapper, TeacherMapper teacherMapper) {
        this.courseDAO = courseDAO;
        this.teacherDAO = teacherDAO;
        this.courseMapper =  courseMapper;
        this.teacherMapper = teacherMapper;
    }

    public BasicLessonDTO toBasicDTO (LessonEntity lessonEntity) {
        return BasicLessonDTO.builder()
                .id(lessonEntity.getId())
                .name(lessonEntity.getName())
                .courseId(lessonEntity.getCourse().getId())
                .description(lessonEntity.getDescription())
                .teacherId(lessonEntity.getTeacher().getId())
                .completed(lessonEntity.isCompleted())
                .build();
    }

    public LessonEntity toEntity (BasicLessonDTO lessonDTO) {
        CourseEntity courseProxy = null;
        if(lessonDTO.courseId() != null) {
            courseProxy = courseDAO.createProxy(lessonDTO.courseId());
        }
        TeacherEntity teacherProxy = null;
        if(lessonDTO.teacherId() != null) {
            teacherProxy = teacherDAO.createProxy(lessonDTO.teacherId());
        }

        return LessonEntity.builder()
                .id(lessonDTO.id())
                .name(lessonDTO.name())
                .course(courseProxy)
                .description(lessonDTO.description())
                .teacher(teacherProxy)
                .completed(lessonDTO.completed())
                .build();
    }

    public DetailedLessonDTO toDetailedDTO(LessonEntity lessonEntity) {
        return DetailedLessonDTO.builder()
                .id(lessonEntity.getId())
                .name(lessonEntity.getName())
                .course(courseMapper.toBasicDTO(lessonEntity.getCourse()))
                .description(lessonEntity.getDescription())
                .teacher(teacherMapper.toBasicDTO(lessonEntity.getTeacher()))
                .completed(lessonEntity.isCompleted())
                .build();
    }
}
