package com.app.dethloff.rest.mappers;

import com.app.dethloff.model.LessonEntity;
import com.app.dethloff.rest.DTO.LessonRequestDTO;
import com.app.dethloff.rest.DTO.LessonResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface LessonMapper {

    // For creation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "course", ignore = true)
    // converter for lessonTime needed
    LessonEntity requestToEntity(LessonRequestDTO request);

    // For updating
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "course", ignore = true)
    // converter for lessonTime needed
    void updateEntityFromRequest(LessonRequestDTO request, @MappingTarget LessonEntity entity);

    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "courseId", source = "course.id")
    // converter for lessonTime needed
    LessonResponseDTO entityToResponse(LessonEntity entity);
}
