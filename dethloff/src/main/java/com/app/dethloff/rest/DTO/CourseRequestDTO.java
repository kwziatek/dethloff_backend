package com.app.dethloff.rest.DTO;

import com.app.dethloff.model.CourseLevel;
import lombok.Builder;

import java.util.List;

@Builder
public record CourseRequestDTO(
    String id,
    String name,
    CourseLevel level,
    String description,
    List<String> studentIds,
    String teacherId,
    String setOfCoursesId
) {}
