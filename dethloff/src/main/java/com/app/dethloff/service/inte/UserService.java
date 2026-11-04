package com.app.dethloff.service.inte;

import com.app.dethloff.rest.DTO.LoginRequestDTO;
import com.app.dethloff.rest.DTO.RegisterRequestDTO;

public interface UserService {
  void register(RegisterRequestDTO request);

  String verify(LoginRequestDTO request);

  void changePassword(String username, String oldPassword, String newPassword);
}
