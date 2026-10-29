package com.app.dethloff.rest.DTO;

import com.app.dethloff.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegisterRequestDTO(
        @NotBlank(message = "Username cannot be empty")
        String username,
        @NotBlank(message = "Password cannot be empty")
        String password,
        @NotNull(message = "Role cannot be null")
        Role role
) {}
