package com.app.dethloff.rest.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SetOfCoursesResponseDTO(
        @NotNull
        String id,
        @NotNull
        String name,
        String description,
        List<String> courseIds
) {}