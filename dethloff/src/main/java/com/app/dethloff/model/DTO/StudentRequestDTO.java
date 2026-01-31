package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record StudentRequestDTO(
        String id,
        String name,
        String surname
) {}
