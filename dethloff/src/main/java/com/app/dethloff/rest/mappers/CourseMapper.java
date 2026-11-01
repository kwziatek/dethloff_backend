package com.app.dethloff.rest.mappers;

import com.app.dethloff.model.CourseEntity;
import com.app.dethloff.rest.DTO.CourseRequestDTO;
import com.app.dethloff.rest.DTO.CourseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface CourseMapper {

    default String courseToId(CourseEntity entity) {
        return entity != null ? entity.getId() : null;
    }

    // For creation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "ofCourses", ignore = true)
    CourseEntity requestToEntity(CourseRequestDTO request);

    // For updating
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "setOfCourses", ignore = true)   // it's for setOfCourses, 'set' is a key word, which makes mapstruct go crazy
    void updateEntityFromRequest(CourseRequestDTO request, @MappingTarget CourseEntity entity);

    @Mapping(target = "studentIds", source = "students")
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "ofCoursesId", source = "setOfCourses.id")
    CourseResponseDTO entityToResponse(CourseEntity entity);
}
