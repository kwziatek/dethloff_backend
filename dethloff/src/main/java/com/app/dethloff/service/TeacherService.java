package com.app.dethloff.service;

import com.app.dethloff.model.DTO.BasicTeacherDTO;
import com.app.dethloff.model.DTO.DetailedTeacherDTO;

import java.util.List;

public interface TeacherService {
    DetailedTeacherDTO get(String id);
    List<BasicTeacherDTO> getAll();
    DetailedTeacherDTO create(DetailedTeacherDTO teacherDTO);
    DetailedTeacherDTO update(DetailedTeacherDTO teacherDTO);
    void delete(String id);
}
