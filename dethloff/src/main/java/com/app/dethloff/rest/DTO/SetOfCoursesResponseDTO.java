package com.app.dethloff.rest.DTO;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record SetOfCoursesResponseDTO(
    @NotNull String id, @NotNull String name, String description, List<String> courseIds) {}
