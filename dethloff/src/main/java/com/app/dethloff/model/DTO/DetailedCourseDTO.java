package com.app.dethloff.model.DTO;

import com.app.dethloff.model.CourseLevel;
import lombok.Builder;

import java.util.List;

@Builder
public record DetailedCourseDTO(
        String id,
        String name,
        String description,
        CourseLevel level,
        BasicTeacherDTO teacher,
        List<BasicStudentDTO> students,
        SetOfCoursesDTO setOfCourses
) {}
