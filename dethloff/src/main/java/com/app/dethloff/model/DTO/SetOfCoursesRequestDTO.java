package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record SetOfCoursesRequestDTO(
        String id,
        String name,
        String description
) {}