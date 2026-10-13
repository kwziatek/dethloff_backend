package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.dao.CourseDAO;
import com.app.dethloff.dao.TeacherDAO;
import com.app.dethloff.model.DTO.LessonRequestDTO;
import com.app.dethloff.model.DTO.LessonResponseDTO;
import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.model.LessonTimeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonMapper {
    private final CourseDAO courseDAO;
    private final TeacherDAO teacherDAO;
    private final CourseMapper courseMapper;
    private final TeacherMapper teacherMapper;

    @Autowired
    public LessonMapper(CourseDAO courseDAO, TeacherDAO teacherDAO, CourseMapper courseMapper, TeacherMapper teacherMapper) {
        this.courseDAO = courseDAO;
        this.teacherDAO = teacherDAO;
        this.courseMapper = courseMapper;
        this.teacherMapper = teacherMapper;
    }

    public LessonEntity toEntity(LessonRequestDTO dto) {
        if (dto == null) return null;

        LessonEntity lesson = LessonEntity.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .completed(dto.completed() != null ? dto.completed() : false) // Null-safe boolean
                .build();

        if (dto.courseId() != null) {
            lesson.setCourse(courseDAO.createProxy(dto.courseId()));
        }

        if (dto.teacherId() != null) {
            lesson.setTeacher(teacherDAO.createProxy(dto.teacherId()));
        }

        if (dto.date() != null || dto.startTime() != null || dto.endTime() != null) {
            LessonTimeEntity timeEntity = LessonTimeEntity.builder()
                    .date(dto.date())
                    .startTime(dto.startTime())
                    .endTime(dto.endTime())
                    .lesson(lesson)
                    .build();
            lesson.setLessonTime(timeEntity);
        }

        return lesson;
    }

    public List<LessonEntity> toEntity(List<LessonRequestDTO> dtoList) {
        if (dtoList == null) return null;
        return dtoList.stream().map(this::toEntity).toList();
    }

    public LessonResponseDTO toLessonResponseDTO(LessonEntity entity) {
        if (entity == null) return null;

        return LessonResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .course(entity.getCourse() != null ? courseMapper.toCourseResponseDTO(entity.getCourse()) : null)
                .description(entity.getDescription())
                .teacher(entity.getTeacher() != null ? teacherMapper.toTeacherResponseDTO(entity.getTeacher()) : null)
                .completed(entity.isCompleted())
                .date(entity.getLessonTime() != null ? entity.getLessonTime().getDate() : null)
                .startTime(entity.getLessonTime() != null ? entity.getLessonTime().getStartTime() : null)
                .endTime(entity.getLessonTime() != null ? entity.getLessonTime().getEndTime() : null)
                .build();
    }

    public List<LessonResponseDTO> toLessonResponseDTO(List<LessonEntity> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toLessonResponseDTO).toList();
    }

    public void updateEntityFromRequest(LessonRequestDTO dto, LessonEntity entity) {
        if (dto == null || entity == null) return;

        // 1. Update basic fields directly from DTO
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.description() != null) entity.setDescription(dto.description());
        if (dto.completed() != null) entity.setCompleted(dto.completed());

        // 2. Update relationships using proxies
        if (dto.courseId() != null) {
            entity.setCourse(courseDAO.createProxy(dto.courseId()));
        }
        if (dto.teacherId() != null) {
            entity.setTeacher(teacherDAO.createProxy(dto.teacherId()));
        }

        // 3. Null-safe nested entity update
        if (dto.date() != null || dto.startTime() != null || dto.endTime() != null) {
            LessonTimeEntity timeEntity = entity.getLessonTime();

            // If the entity doesn't have a time entity yet, create and attach it
            if (timeEntity == null) {
                timeEntity = new LessonTimeEntity();
                timeEntity.setLesson(entity);
                entity.setLessonTime(timeEntity);
            }

            // Safely update the fields
            if (dto.date() != null) timeEntity.setDate(dto.date());
            if (dto.startTime() != null) timeEntity.setStartTime(dto.startTime());
            if (dto.endTime() != null) timeEntity.setEndTime(dto.endTime());
        }
    }
}