package com.app.dethloff.model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record LessonResponseDTO(
        @NotNull
        String id,
        @NotNull
        String name,
        @NotNull
        CourseResponseDTO course,
        String description,
        @NotNull
        TeacherResponseDTO teacher,
        @NotNull
        Boolean completed,
        @NotNull
        LocalDate date,
        @NotNull
        LocalTime startTime,
        @NotNull
        LocalTime endTime
) {}
