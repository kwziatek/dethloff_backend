package com.app.dethloff.rest.DTO;

import com.app.dethloff.rest.DTO.validation_groups.CreateAction;
import com.app.dethloff.rest.DTO.validation_groups.UpdateAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record LessonRequestDTO(
    @NotNull(groups = UpdateAction.class, message = "LESSON_NOT_FOUND")
        @Null(groups = CreateAction.class, message = "LESSON_ID_MUST_BE_NULL")
        String id,
    @Size(
            min = 2,
            groups = {CreateAction.class, UpdateAction.class},
            message = "NAME_MUST_BE_LONGER_THAN_1_CHAR")
        @NotNull(groups = CreateAction.class)
        String name,
    @NotNull(groups = CreateAction.class) String courseId,
    String description,
    @NotNull(groups = CreateAction.class) String teacherId,
    @NotNull(groups = CreateAction.class) Boolean completed,
    @NotNull(groups = CreateAction.class) LocalDate date,
    @NotNull(groups = CreateAction.class) LocalTime startTime,
    @NotNull(groups = CreateAction.class) LocalTime endTime) {}
