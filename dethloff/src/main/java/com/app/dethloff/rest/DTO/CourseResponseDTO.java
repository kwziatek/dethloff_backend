package com.app.dethloff.rest.DTO;

import com.app.dethloff.model.CourseLevel;
import lombok.Builder;

import java.util.List;

@Builder
public record CourseResponseDTO(
        String id,
        String name,
        String description,
        CourseLevel level,
        String teacherId,
        List<String> studentIds,
        String setOfCoursesId,
        String room,
        Integer totalCourseTime,
        Integer completedCourseTime
) {}
