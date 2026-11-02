package com.app.dethloff.rest.DTO;

import com.app.dethloff.model.CourseLevel;
import java.util.List;
import lombok.Builder;

@Builder
public record CourseRequestDTO(
    String id,
    String name,
    CourseLevel level,
    String description,
    List<String> studentIds,
    String teacherId,
    String setOfCoursesId,
    String room,
    Integer totalCourseTime,
    Integer completedCourseTime) {}
