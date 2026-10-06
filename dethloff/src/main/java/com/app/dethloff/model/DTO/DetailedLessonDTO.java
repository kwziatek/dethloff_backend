package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record DetailedLessonDTO(
        String id,
        String name,
        BasicCourseDTO course,
        String description,
        BasicTeacherDTO teacher,
        Boolean completed
) {}
