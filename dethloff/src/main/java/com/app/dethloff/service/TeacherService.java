package com.app.dethloff.service;

import com.app.dethloff.rest.DTO.TeacherRequestDTO;
import com.app.dethloff.rest.DTO.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {
    TeacherResponseDTO get(String id);
    List<TeacherResponseDTO> getAll();
    TeacherResponseDTO create(TeacherRequestDTO teacherRequestDTO);
    TeacherResponseDTO update(TeacherRequestDTO teacherRequestDTO);
    void delete(String id);
}
