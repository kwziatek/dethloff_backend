package com.app.dethloff.service;

import com.app.dethloff.model.DTO.StudentRequestDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    StudentResponseDTO create(StudentRequestDTO studentDTO);
    StudentResponseDTO get(String id);
    List<StudentResponseDTO> getAll();
    void delete(String id);
    StudentResponseDTO update(StudentRequestDTO studentDTO);

}
