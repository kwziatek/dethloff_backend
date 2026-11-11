package com.app.dethloff.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.dethloff.model.UserEntity;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
  void register_ShouldReturnNoContent() throws Exception {
    RegisterRequestDTO request = RegisterRequestDTO.builder()
        .username("user1")
        .password("password123")
        .role(com.app.dethloff.model.Role.ROLE_USER)
        .build();

    mockMvc.perform(post("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNoContent());
  }

  @Test
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
        .username("user1")
        .password("password123")
        .build();

    String token = "mockedToken";
    when(userService.verify(any())).thenReturn(token);

    mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").value(token));
  }

  @Test
  void login_ShouldReturnUnauthorized_WhenInvalidCredentials() throws Exception {
    LoginRequestDTO request = LoginRequestDTO.builder()
        .username("user1")
        .password("wrongPassword")
        .build();

    when(userService.verify(any())).thenThrow(new com.app.dethloff.exceptions.model.InvalidLoginOrPasswordException());

    mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isUnauthorized());
  }

  // 3. changePassword
  @Test
  void changePassword_ShouldReturnNoContent() throws Exception {
    ChangePasswordRequestDTO request = ChangePasswordRequestDTO.builder()
        .oldPassword("oldPassword123")
        .newPassword("newPassword123")
        .build();

    Authentication authentication = new UsernamePasswordAuthenticationToken("user1", "oldPassword123");

    mockMvc.perform(post("/api/changePassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .principal(authentication))
        .andExpect(status().isNoContent());
  }

  @Test
  void changePassword_ShouldReturnUnauthorized_WhenInvalidOldPassword() throws Exception {
    ChangePasswordRequestDTO request = ChangePasswordRequestDTO.builder()
        .oldPassword("wrongOldPassword123")
        .newPassword("newPassword123")
        .build();

    Authentication authentication = new UsernamePasswordAuthenticationToken("user1", "wrongOldPassword123");

    when(userService.changePassword(any(), any(), any())).thenThrow(new com.app.dethloff.exceptions.model.InvalidPasswordException());

    mockMvc.perform(post("/api/changePassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .principal(authentication))
        .andExpect(status().isUnauthorized());
  }

  // 4. getCurrentUser
  @Test
  void getCurrentUser_ShouldReturnUser() throws Exception {
    Authentication authentication = new UsernamePasswordAuthenticationToken("user1", "password123");

    mockMvc.perform(get("/api/auth/me")
            .principal(authentication))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.username").value("user1"));
  }
}
