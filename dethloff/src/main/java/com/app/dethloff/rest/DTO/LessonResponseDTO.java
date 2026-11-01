package com.app.dethloff.rest.DTO;

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
        String courseId,
        String description,
        @NotNull
        String teacherId,
        @NotNull
        Boolean completed,
        @NotNull
        LocalDate date,
        @NotNull
        LocalTime startTime,
        @NotNull
        LocalTime endTime
) {}
