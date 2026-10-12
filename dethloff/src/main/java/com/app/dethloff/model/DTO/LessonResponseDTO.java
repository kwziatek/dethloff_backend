package com.app.dethloff.model.DTO;

import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record LessonResponseDTO(
        String id,
        String name,
        CourseResponseDTO course,
        String description,
        TeacherResponseDTO teacher,
        Boolean completed,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {}
