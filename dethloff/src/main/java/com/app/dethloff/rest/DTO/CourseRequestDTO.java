package com.app.dethloff.rest.DTO;

import com.app.dethloff.model.CourseLevel;
import com.app.dethloff.rest.DTO.validation_groups.CreateAction;
import com.app.dethloff.rest.DTO.validation_groups.UpdateAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
public record CourseRequestDTO(
    @NotNull(groups = UpdateAction.class, message = "COURSE_NOT_FOUND")
        @Null(groups = CreateAction.class, message = "COURSE_ID_MUST_BE_NULL")
        String id,
    @Size(
            min = 2,
            groups = {CreateAction.class, UpdateAction.class},
            message = "NAME_MUST_BE_LONGER_THAN_1_CHAR")
        @NotNull(groups = CreateAction.class)
        String name,
    @NotNull(groups = CreateAction.class) CourseLevel level,
    String description,
    @NotNull(groups = CreateAction.class) List<String> studentIds,
    @NotNull(groups = CreateAction.class) String teacherId,
    @NotNull(groups = CreateAction.class) String setOfCoursesId,
    @NotNull(groups = CreateAction.class) String room,
    @NotNull(groups = CreateAction.class) Integer totalCourseTime,
    @NotNull(groups = CreateAction.class) Integer completedCourseTime) {}
