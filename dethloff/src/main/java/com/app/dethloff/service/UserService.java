package com.app.dethloff.service;

import com.app.dethloff.model.DTO.LoginRequest;
import com.app.dethloff.model.DTO.RegisterRequest;

public interface UserService {
    void register(RegisterRequest request);
    String verify(LoginRequest request);
    void changePassword(String username, String oldPassword, String newPassword);
}
