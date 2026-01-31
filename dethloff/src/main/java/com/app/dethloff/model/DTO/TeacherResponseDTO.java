package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record TeacherResponseDTO(
        String id,
        String name,
        String surname
) {}
