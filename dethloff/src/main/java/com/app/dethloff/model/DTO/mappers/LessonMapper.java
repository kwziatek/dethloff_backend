package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.DTO.BasicLessonDTO;
import com.app.dethloff.model.DTO.DetailedLessonDTO;
import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.model.LessonTimeEntity;
import com.app.dethloff.model.TeacherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
                .date(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getDate() : null)
                .startTime(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getStartTime() : null)
                .endTime(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getEndTime() : null)
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

        LessonEntity lesson = LessonEntity.builder()
                .id(lessonDTO.id())
                .name(lessonDTO.name())
                .course(courseProxy)
                .description(lessonDTO.description())
                .teacher(teacherProxy)
                .completed(lessonDTO.completed())
                .build();

        if (lessonDTO.date() != null) {
            LessonTimeEntity timeEntity = LessonTimeEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .date(lessonDTO.date())
                    .startTime(lessonDTO.startTime())
                    .endTime(lessonDTO.endTime())
                    .lesson(lesson)
                    .build();
            lesson.setLessonTime(timeEntity);
        }

        return lesson;
    }

    public DetailedLessonDTO toDetailedDTO(LessonEntity lessonEntity) {
        return DetailedLessonDTO.builder()
                .id(lessonEntity.getId())
                .name(lessonEntity.getName())
                .course(courseMapper.toBasicDTO(lessonEntity.getCourse()))
                .description(lessonEntity.getDescription())
                .teacher(teacherMapper.toBasicDTO(lessonEntity.getTeacher()))
                .completed(lessonEntity.isCompleted())
                .date(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getDate() : null)
                .startTime(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getStartTime() : null)
                .endTime(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getEndTime() : null)
                .build();
    }

    public List<DetailedLessonDTO> toDetailedDTO(List<LessonEntity> lessonEntities) {
        return lessonEntities.stream().map(this::toDetailedDTO).toList();
    }
}
