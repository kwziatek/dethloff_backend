package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.SetOfCoursesDTO;
import com.app.dethloff.model.SetOfCoursesEntity;
import org.springframework.stereotype.Component;

@Component
public class SetOfCoursesMapper {
    public SetOfCoursesMapper(){

    }

    public SetOfCoursesDTO toDTO(SetOfCoursesEntity entity) {
        return SetOfCoursesDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public SetOfCoursesEntity toEntity(SetOfCoursesDTO dto) {
        return SetOfCoursesEntity.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .build();
    }
}
