package com.app.dethloff.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.dethloff.rest.DTO.ChangePasswordRequestDTO;
import com.app.dethloff.rest.DTO.LoginRequestDTO;
import com.app.dethloff.rest.DTO.RegisterRequestDTO;
import com.app.dethloff.service.inte.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.mvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @MockitoBean
  private UserService userService;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  // 1. register
  @Test
  @WithMockUser(roles = "ADMIN")
  void register_ShouldReturnNoContent() throws Exception {
    RegisterRequestDTO request = RegisterRequestDTO.builder()
        .username("user")
        .password("password")
        .email("user@example.com")
        .build();

    mockMvc.perform(post("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void register_ShouldReturnBadRequest_WhenInputIsInvalid() throws Exception {
    RegisterRequestDTO invalidRequest = RegisterRequestDTO.builder().build();

    mockMvc.perform(post("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  // 2. login
  @Test
  void login_ShouldReturnToken() throws Exception {
    LoginRequestDTO request = LoginRequestDTO.builder()
        .username("user")
        .password("password")
        .build();

    String token = "token";
    when(userService.verify(any())).thenReturn(token);

    mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().string(token));
  }

  @Test
  void login_ShouldReturnUnauthorized_WhenCredentialsAreInvalid() throws Exception {
    LoginRequestDTO request = LoginRequestDTO.builder()
        .username("user")
        .password("password")
        .build();

    when(userService.verify(any())).thenThrow(new RuntimeException("Invalid credentials"));

    mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isUnauthorized());
  }

  // 3. changePassword
  @Test
  @WithMockUser
  void changePassword_ShouldReturnNoContent() throws Exception {
    ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
        .oldPassword("oldPassword")
        .newPassword("newPassword")
        .build();

    mockMvc.perform(post("/api/changePassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser
  void changePassword_ShouldReturnBadRequest_WhenOldPasswordIsIncorrect() throws Exception {
    ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
        .oldPassword("oldPassword")
        .newPassword("newPassword")
        .build();

    when(userService.changePassword(any(), any(), any())).thenThrow(new RuntimeException("Invalid old password"));

    mockMvc.perform(post("/api/changePassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isBadRequest());
  }

  // 4. getCurrentUser
  @Test
  @WithMockUser
  void getCurrentUser_ShouldReturnUser() throws Exception {
    mockMvc.perform(get("/api/auth/me"))
        .andExpect(status().isOk());
  }
}
