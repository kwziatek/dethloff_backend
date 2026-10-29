package com.app.dethloff.service;

import com.app.dethloff.rest.DTO.StudentRequestDTO;
import com.app.dethloff.rest.DTO.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    StudentResponseDTO create(StudentRequestDTO studentRequestDTO);
    StudentResponseDTO get(String id);
    List<StudentResponseDTO> getAll();
    void delete(String id);
    StudentResponseDTO update(StudentRequestDTO studentRequestDTO);

}
