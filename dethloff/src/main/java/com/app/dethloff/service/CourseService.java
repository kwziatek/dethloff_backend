package com.app.dethloff.service;

import com.app.dethloff.DTO.CourseDTO;

import java.util.List;

public interface CourseService {
    void create(CourseDTO courseDTO);
    CourseDTO get(String id);
    List<CourseDTO> getAll();
    void delete(String id);
    void update(CourseDTO courseDTO);
}
