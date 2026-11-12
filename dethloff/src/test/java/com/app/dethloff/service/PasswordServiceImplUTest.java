package com.app.dethloff.service;

import static org.junit.jupiter.api.Assertions.*;

import com.app.dethloff.service.impl.PasswordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PasswordServiceImplUTest {

  private PasswordServiceImpl passwordService;

  @BeforeEach
  void setUp() {
    passwordService = new PasswordServiceImpl();
  }

  @Test
  void validateNewPassword_ShouldReturnTrue_WhenPasswordIsValid() {
    // given: length >= 14, contains uppercase, contains >= 3 special signs
    String validPassword = "Password123!!!";

    // when
    boolean result = passwordService.validateNewPassword(validPassword);

    // then
    assertTrue(result);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "Short1!", // Too short (< 14)
        "alllowercase123!!!", // No uppercase
        "Password123!", // Not enough special signs (< 3)
        "123456789012345" // No uppercase, no special signs
      })
  void validateNewPassword_ShouldReturnFalse_WhenPasswordIsInvalid(String invalidPassword) {
    // when
    boolean result = passwordService.validateNewPassword(invalidPassword);

    // then
    assertFalse(result);
  }

  @Test
  void validateNewPassword_ShouldReturnTrue_WhenPasswordIsExactlyAtLimits() {
    // length 14, 1 uppercase, 3 special signs
    String boundaryPassword = "Ab123456789!!!";

    assertTrue(passwordService.validateNewPassword(boundaryPassword));
  }
}
