package com.app.dethloff.model.DTO;

import com.app.dethloff.model.CourseLevel;
import lombok.Builder;

@Builder
public record CourseRequestDTO(
    String id,
    String name,
    CourseLevel level,
    String description,
    String teacherId
) {}
