package com.app.dethloff.model.DTO;

import lombok.Builder;

@Builder
public record StudentResponseDTO(
        String id,
        String name,
        String surname
) {}
