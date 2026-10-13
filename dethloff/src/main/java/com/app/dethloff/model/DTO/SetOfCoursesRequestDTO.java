package com.app.dethloff.model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SetOfCoursesRequestDTO(
        @NotNull
        String id,
        @NotNull
        String name,
        String description
) {}