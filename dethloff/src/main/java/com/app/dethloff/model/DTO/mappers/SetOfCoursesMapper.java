package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.SetOfCoursesRequestDTO;
import com.app.dethloff.model.DTO.SetOfCoursesResponseDTO;
import com.app.dethloff.model.SetOfCoursesEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SetOfCoursesMapper {
    public SetOfCoursesMapper(){

    }

    public SetOfCoursesEntity toEntity(SetOfCoursesRequestDTO setOfCoursesRequestDTO) {
        return SetOfCoursesEntity.builder()
                .id(setOfCoursesRequestDTO.id())
                .name(setOfCoursesRequestDTO.name())
                .description(setOfCoursesRequestDTO.description())
                .build();
    }

    public List<SetOfCoursesEntity> toEntity(List<SetOfCoursesRequestDTO> setOfCoursesRequestDTOList) {
        return setOfCoursesRequestDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    public SetOfCoursesResponseDTO toSetOfCoursesResponseDTO(SetOfCoursesEntity entity) {
        return SetOfCoursesResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public List<SetOfCoursesResponseDTO> toSetOfCoursesResponseDTO(List<SetOfCoursesEntity> entity) {
        return entity.stream()
                .map(this::toSetOfCoursesResponseDTO)
                .toList();
    }

    public void updateEntityFromRequest (SetOfCoursesRequestDTO setOfCoursesRequestDTO, SetOfCoursesEntity entity) {
        if(setOfCoursesRequestDTO == null) return;

        SetOfCoursesEntity newSetOfCoursesEntity = toEntity(setOfCoursesRequestDTO);

        if(newSetOfCoursesEntity.getName() != null) entity.setName(newSetOfCoursesEntity.getName());
        if(newSetOfCoursesEntity.getDescription() != null) entity.setDescription(newSetOfCoursesEntity.getDescription());
        if(newSetOfCoursesEntity.getCourses() != null) entity.setCourses(newSetOfCoursesEntity.getCourses());
    }
}
