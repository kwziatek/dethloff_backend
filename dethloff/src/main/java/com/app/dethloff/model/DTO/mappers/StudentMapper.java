package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.StudentRequestDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;
import com.app.dethloff.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {
    public StudentMapper() {

    }
    public StudentResponseDTO toDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .build();
    }

    public Student toStudent(StudentRequestDTO studentDTO) {
        return Student.builder()
                .id(studentDTO.id())
                .name(studentDTO.name())
                .surname(studentDTO.surname())
                .build();
    }

    public List<StudentResponseDTO> toDTO(List<Student> students) {
        return students.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<Student> toStudent(List<StudentRequestDTO> studentDTOs) {
        return studentDTOs.stream()
                .map(this::toStudent)
                .toList();
    }
}
