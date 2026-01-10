package com.app.dethloff.DTO;

import lombok.Builder;

@Builder
public record StudentDTO(
        String id,
        String name,
        String surname
) {}
