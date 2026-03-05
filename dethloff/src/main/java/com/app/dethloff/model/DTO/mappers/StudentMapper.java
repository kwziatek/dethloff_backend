package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.StudentRequestDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
import com.app.dethloff.model.StudentEntity;
import com.app.dethloff.model.pesel.Pesel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {
    public StudentMapper() {

    }
    public StudentResponseDTO toDTO(StudentEntity student) {
        String pesel = student.getPesel() != null ? student.getPesel().toString() : null;

        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .isActive(student.getIsActive())
                .pesel(pesel)
                .birthDate(student.getBirthDate())
                .placeOfBirth(student.getPlaceOfBirth())
                .gender(student.getGender())
                .build();
    }

    public StudentEntity toStudent(StudentRequestDTO studentDTO) {
        return StudentEntity.builder()
                .id(studentDTO.id())
                .name(studentDTO.name())
                .surname(studentDTO.surname())
                .isActive(studentDTO.isActive())
                .pesel(new Pesel(studentDTO.pesel()))
                .birthDate(studentDTO.birthDate())
                .placeOfBirth(studentDTO.placeOfBirth())
                .gender(studentDTO.gender())
                .build();
    }

    public List<StudentResponseDTO> toDTO(List<StudentEntity> students) {
        return students.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<StudentEntity> toStudent(List<StudentRequestDTO> studentDTOs) {
        return studentDTOs.stream()
                .map(this::toStudent)
                .toList();
    }
}
