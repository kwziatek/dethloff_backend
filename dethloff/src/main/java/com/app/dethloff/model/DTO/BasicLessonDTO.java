package com.app.dethloff.model.DTO;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record BasicLessonDTO(
        String id,
        String name,
        String courseId,
        String description,
        String teacherId,
        Boolean completed,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {}
