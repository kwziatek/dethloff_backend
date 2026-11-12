package com.app.dethloff.service;

import static org.junit.jupiter.api.Assertions.*;

import com.app.dethloff.model.Role;
import com.app.dethloff.service.impl.JWTServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTServiceImplUTest {

  private JWTServiceImpl jwtService;
  private UserDetails userDetails;

  @BeforeEach
  void setUp() {
    jwtService = new JWTServiceImpl();
    userDetails = Mockito.mock(UserDetails.class);
    Mockito.when(userDetails.getUsername()).thenReturn("testUser");
  }

  @Test
  void generateToken_ShouldCreateValidToken() {
    // given
    String username = "testUser";
    Role role = Role.ROLE_ADMIN;

    // when
    String token = jwtService.generateToken(username, role);

    // then
    assertNotNull(token);
    assertFalse(token.isEmpty());
    assertEquals(username, jwtService.extractUsername(token));
  }

  @Test
  void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
    // given
    String username = "testUser";
    String token = jwtService.generateToken(username, Role.ROLE_ADMIN);

    // when
    boolean isValid = jwtService.validateToken(token, userDetails);

    // then
    assertTrue(isValid);
  }

  @Test
  void validateToken_ShouldReturnFalse_WhenUsernameDoesNotMatch() {
    // given
    String username = "differentUser";
    String token = jwtService.generateToken(username, Role.ROLE_ADMIN);

    // when
    boolean isValid = jwtService.validateToken(token, userDetails);

    // then
    assertFalse(isValid);
  }

  @Test
  void validateToken_ShouldReturnFalse_WhenTokenIsMalformed() {
    // given
    String malformedToken = "not.a.real.jwt.token";

    // when && then
    assertThrows(Exception.class, () -> jwtService.validateToken(malformedToken, userDetails));
  }
}
