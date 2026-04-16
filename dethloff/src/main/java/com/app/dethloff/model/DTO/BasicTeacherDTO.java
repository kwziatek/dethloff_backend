package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record BasicTeacherDTO(
        String id,
        String name,
        String surname,
        String pesel,
        Boolean isActive
) {}
