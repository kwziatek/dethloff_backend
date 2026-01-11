package com.app.dethloff.service;

import com.app.dethloff.DTO.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO create(StudentDTO studentDTO);
    StudentDTO get(String id);
    List<StudentDTO> getAll();
    void delete(String id);
    StudentDTO update(StudentDTO studentDTO);

}
