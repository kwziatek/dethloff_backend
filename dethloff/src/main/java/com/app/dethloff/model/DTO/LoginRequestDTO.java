package com.app.dethloff.model.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO
(
        @NotBlank(message = "Username cannot be empty")
        String username,
        @NotBlank(message = "Password cannot be empty")
        String password
) {}
