package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.TeacherRequestDTO;
import com.app.dethloff.model.DTO.TeacherResponseDTO;
import com.app.dethloff.model.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherMapper {

    public TeacherResponseDTO toDTO(Teacher teacher) {
        return TeacherResponseDTO.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .build();
    }

    public Teacher toTeacher(TeacherRequestDTO teacherDTO) {
        return Teacher.builder()
                .id(teacherDTO.id())
                .name(teacherDTO.name())
                .surname(teacherDTO.surname())
                .build();
    }

    public List<TeacherResponseDTO> toDTO(List<Teacher> teachers) {
        return teachers.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<Teacher> toTeacher(List<TeacherRequestDTO> teacherDTOs) {
        return teacherDTOs.stream()
                .map(this::toTeacher)
                .toList();
    }
}
