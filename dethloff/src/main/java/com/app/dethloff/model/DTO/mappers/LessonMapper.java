package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.model.DTO.*;
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


    public LessonEntity toEntity (LessonRequestDTO lessonDTO) {
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

    public List<LessonEntity> toEntity(List<LessonRequestDTO> lessonRequestDTOList) {
        return lessonRequestDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    public LessonResponseDTO toLessonResponseDTO(LessonEntity lessonEntity) {
        TeacherResponseDTO teacherResponseDTO = null;
        CourseResponseDTO courseResponseDTO = null;

        if(lessonEntity.getCourse() != null) {
            courseResponseDTO = courseMapper.toCourseResponseDTO(lessonEntity.getCourse());
        }
        if(lessonEntity.getTeacher() != null) {
            teacherResponseDTO = teacherMapper.toTeacherResponseDTO(lessonEntity.getTeacher());
        }

        return LessonResponseDTO.builder()
                .id(lessonEntity.getId())
                .name((lessonEntity.getName()))
                .course(courseResponseDTO)
                .description(lessonEntity.getDescription())
                .teacher(teacherResponseDTO)
                .completed(lessonEntity.isCompleted())
                .date(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getDate() : null)
                .startTime(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getStartTime() : null)
                .endTime(lessonEntity.getLessonTime() != null ? lessonEntity.getLessonTime().getEndTime() : null)
                .build();
    }

    public List<LessonResponseDTO> toLessonResponseDTO(List<LessonEntity> lessonEntities) {
        return lessonEntities.stream().map(this::toLessonResponseDTO).toList();
    }

    public void updateEntityFromRequest(LessonRequestDTO dto, LessonEntity entity) {
        if (dto == null) return;
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setCompleted(dto.completed());
        if (dto.date() != null) {
            if (entity.getLessonTime() == null) {
                com.app.dethloff.model.LessonTimeEntity time = com.app.dethloff.model.LessonTimeEntity.builder()
                        .id(java.util.UUID.randomUUID().toString())
                        .date(dto.date())
                        .startTime(dto.startTime())
                        .endTime(dto.endTime())
                        .lesson(entity)
                        .build();
                entity.setLessonTime(time);
            } else {
                entity.getLessonTime().setDate(dto.date());
                entity.getLessonTime().setStartTime(dto.startTime());
                entity.getLessonTime().setEndTime(dto.endTime());
            }
        }
    }
}
