package com.app.dethloff.service;

import com.app.dethloff.model.DTO.TeacherRequestDTO;
import com.app.dethloff.model.DTO.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {
    TeacherResponseDTO get(String id);
    List<TeacherResponseDTO> getAll();
    TeacherResponseDTO create(TeacherRequestDTO teacherDTO);
    TeacherResponseDTO update(TeacherRequestDTO teacherDTO);
    void delete(String id);
}
