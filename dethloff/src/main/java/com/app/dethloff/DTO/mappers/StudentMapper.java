package com.app.dethloff.DTO.mappers;

import com.app.dethloff.DTO.StudentDTO;
import com.app.dethloff.model.Student;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StudentMapper {
    public StudentMapper() {

    }
    public static StudentDTO toDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .build();
    }

    public static Student toStudent(StudentDTO studentDTO) {
        return Student.builder()
                .id(studentDTO.id())
                .name(studentDTO.name())
                .surname(studentDTO.surname())
                .build();
    }

    public static List<StudentDTO> toDTO(List<Student> students) {
        return students.stream()
                .map(StudentMapper::toDTO)
                .toList();
    }
}
