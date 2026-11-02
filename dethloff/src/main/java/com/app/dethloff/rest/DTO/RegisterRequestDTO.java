package com.app.dethloff.rest.DTO;

import com.app.dethloff.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequestDTO(
    @NotBlank(message = "USERNAME_CANNOT_BE_EMPTY") String username,
    @NotBlank(message = "PASSWORD_CANNOT_BE_EMPTY") String password,
    @NotBlank(message = "ROLE_CANNOT_BE_EMPTY") Role role) {}
