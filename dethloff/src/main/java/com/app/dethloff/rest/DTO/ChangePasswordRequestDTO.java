package com.app.dethloff.rest.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ChangePasswordRequestDTO(
    @NotBlank(message = "OLD_PASSWORD_CANNOT_BE_NULL") String oldPassword,
    @NotBlank(message = "NEW_PASSWORD_CANNOT_BE_NULL") String newPassword) {}
