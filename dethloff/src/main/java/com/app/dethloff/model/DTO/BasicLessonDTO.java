package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record BasicLessonDTO(
        String id,
        String name,
        String courseId,
        String description,
        String teacherId,
        Boolean completed
) {}
