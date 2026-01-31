package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record TeacherRequestDTO(
        String id,
        String name,
        String surname
) {}
