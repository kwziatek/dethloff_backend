package com.app.dethloff.model.DTO;

import com.app.dethloff.model.CourseLevel;
import lombok.Builder;

import java.util.List;

@Builder
public record CourseResponseDTO(
        String id,
        String name,
        String description,
        CourseLevel level,
        TeacherResponseDTO teacher,
        List<StudentResponseDTO> students
) {}
