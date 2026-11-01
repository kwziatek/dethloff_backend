package com.app.dethloff.rest.mappers;

import com.app.dethloff.model.SetOfCoursesEntity;
import com.app.dethloff.rest.DTO.SetOfCoursesRequestDTO;
import com.app.dethloff.rest.DTO.SetOfCoursesResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SetOfCoursesMapper {

    // For creation
    @Mapping(target = "id", ignore = true)
    SetOfCoursesEntity requestToEntity(SetOfCoursesRequestDTO request);

    // For update
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(SetOfCoursesRequestDTO request, @MappingTarget SetOfCoursesEntity entity);


    SetOfCoursesResponseDTO entityToResponse(SetOfCoursesEntity entity);
}
