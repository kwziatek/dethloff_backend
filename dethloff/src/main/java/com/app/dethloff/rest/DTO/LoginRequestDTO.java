package com.app.dethloff.rest.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequestDTO(
    @NotBlank(message = "USERNAME_CANNOT_BE_EMPTY") String username,
    @NotBlank(message = "PASSWORD_CANNOT_BE_EMPTY") String password) {}
