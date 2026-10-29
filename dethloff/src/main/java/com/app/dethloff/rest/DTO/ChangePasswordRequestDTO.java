package com.app.dethloff.rest.DTO;

public record ChangePasswordRequestDTO(
        String oldPassword,
        String newPassword
) {}
