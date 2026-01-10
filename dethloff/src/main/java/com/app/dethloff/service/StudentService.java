package com.app.dethloff.service;

import com.app.dethloff.DTO.StudentDTO;
import com.app.dethloff.model.Student;

import java.util.List;

public interface StudentService {
    void create(StudentDTO studentDTO);
    StudentDTO get(String id);
    List<StudentDTO> getAll();
    void delete(String id);
    void update(StudentDTO studentDTO);

}
