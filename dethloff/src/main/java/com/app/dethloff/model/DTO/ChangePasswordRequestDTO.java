package com.app.dethloff.model.DTO;

public record ChangePasswordRequestDTO(
        String oldPassword,
        String newPassword
) {}
