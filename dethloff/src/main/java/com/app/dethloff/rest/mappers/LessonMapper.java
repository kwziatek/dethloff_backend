package com.app.dethloff.rest.mappers;

import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonMapper {

  // For creation
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "teacher", ignore = true)
  @Mapping(target = "course", ignore = true)
  @Mapping(target = "lessonTime.date", source = "date")
  @Mapping(target = "lessonTime.startTime", source = "startTime")
  @Mapping(target = "lessonTime.endTime", source = "endTime")
  LessonEntity requestToEntity(LessonRequestDTO request);

  // For updating
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "teacher", ignore = true)
  @Mapping(target = "course", ignore = true)
  @Mapping(target = "lessonTime.date", source = "date")
  @Mapping(target = "lessonTime.startTime", source = "startTime")
  @Mapping(target = "lessonTime.endTime", source = "endTime")
  void updateEntityFromRequest(LessonRequestDTO request, @MappingTarget LessonEntity entity);

  @Mapping(target = "teacherId", source = "teacher.id")
  @Mapping(target = "courseId", source = "course.id")
  @Mapping(target = "startTime", source = "lessonTime.startTime")
  @Mapping(target = "endTime", source = "lessonTime.endTime")
  @Mapping(target = "date", source = "lessonTime.date")
  LessonResponseDTO entityToResponse(LessonEntity entity);

  // it's crucial because LessonTimeEntity is the owning side, so it needs the LessonEntity
  // reference
  // hibernate doesn't care about mappedBy
  @AfterMapping
  default void linkLessonTime(@MappingTarget LessonEntity lesson) {
    if (lesson.getLessonTime() != null) {
      lesson.getLessonTime().setLesson(lesson);
    }
  }
}
