package com.app.dethloff.service;

import com.app.dethloff.model.DTO.BasicStudentDTO;
import com.app.dethloff.model.DTO.DetailedStudentDTO;

import java.util.List;

public interface StudentService {
    DetailedStudentDTO create(DetailedStudentDTO studentDTO);
    DetailedStudentDTO get(String id);
    List<BasicStudentDTO> getAll();
    void delete(String id);
    DetailedStudentDTO update(DetailedStudentDTO studentDTO);

}
