package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record SetOfCoursesResponseDTO(
        String id,
        String name,
        String description
) {}