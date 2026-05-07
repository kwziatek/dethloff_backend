package com.app.dethloff.model.DTO;

import com.app.dethloff.login.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotBlank(message = "Username cannot be empty")
        String username,
        @NotBlank(message = "Password cannot be empty")
        String password,
        @NotNull(message = "Role cannot be null")
        Role role
) {}
