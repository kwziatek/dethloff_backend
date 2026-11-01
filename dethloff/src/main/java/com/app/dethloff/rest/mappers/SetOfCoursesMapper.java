package com.app.dethloff.rest.mappers;

import com.app.dethloff.model.SetOfCoursesEntity;
import com.app.dethloff.rest.DTO.SetOfCoursesRequestDTO;
import com.app.dethloff.rest.DTO.SetOfCoursesResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface SetOfCoursesMapper {

    default String setOfCoursesToId(SetOfCoursesEntity entity) {
        return entity != null ? entity.getId() : null;
    }

    // For creation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    SetOfCoursesEntity requestToEntity(SetOfCoursesRequestDTO request);

    // For update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void updateEntityFromRequest(SetOfCoursesRequestDTO request, @MappingTarget SetOfCoursesEntity entity);

    @Mapping(target = "courseIds", source = "courses")
    SetOfCoursesResponseDTO entityToResponse(SetOfCoursesEntity entity);
}
