package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.TeacherRequestDTO;
import com.app.dethloff.model.DTO.TeacherResponseDTO;
import com.app.dethloff.model.TeacherEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherMapper {

    public TeacherResponseDTO toDTO(TeacherEntity teacher) {
        return TeacherResponseDTO.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .build();
    }

    public TeacherEntity toTeacher(TeacherRequestDTO teacherDTO) {
        return TeacherEntity.builder()
                .id(teacherDTO.id())
                .name(teacherDTO.name())
                .surname(teacherDTO.surname())
                .build();
    }

    public List<TeacherResponseDTO> toDTO(List<TeacherEntity> teachers) {
        return teachers.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<TeacherEntity> toTeacher(List<TeacherRequestDTO> teacherDTOs) {
        return teacherDTOs.stream()
                .map(this::toTeacher)
                .toList();
    }
}
