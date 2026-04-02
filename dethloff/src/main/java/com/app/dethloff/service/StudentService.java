package com.app.dethloff.service;

import com.app.dethloff.model.DTO.DetailedStudentDTO;
import com.app.dethloff.model.DTO.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    DetailedStudentDTO create(DetailedStudentDTO studentDTO);
    DetailedStudentDTO get(String id);
    List<StudentResponseDTO> getAll();
    void delete(String id);
    DetailedStudentDTO update(DetailedStudentDTO studentDTO);

}
