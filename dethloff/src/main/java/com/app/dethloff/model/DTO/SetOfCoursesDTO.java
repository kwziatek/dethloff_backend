package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record SetOfCoursesDTO(
        String id,
        String name,
        String description
) {}