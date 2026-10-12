package com.app.dethloff.model.DTO;

import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record DetailedLessonDTO(
        String id,
        String name,
        BasicCourseDTO course,
        String description,
        BasicTeacherDTO teacher,
        Boolean completed,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {}
