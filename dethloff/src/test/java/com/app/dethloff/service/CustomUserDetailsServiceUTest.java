package com.app.dethloff.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.app.dethloff.exceptions.model.UserNotFoundException;
import com.app.dethloff.model.UserEntity;
import com.app.dethloff.model.UserPrincipal;
import com.app.dethloff.repository.UserRepository;
import com.app.dethloff.service.impl.CustomUserDetailsService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceUTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private CustomUserDetailsService customUserDetailsService;

  @Test
  void loadUserByUsername_ShouldReturnUserPrincipal_WhenUserExists() {
    // given
    String username = "testuser";
    UserEntity user = UserEntity.builder()
        .username(username)
        .password("password123")
        .build();

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    // when
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

    // then
    assertNotNull(userDetails);
    assertTrue(userDetails instanceof UserPrincipal);
    assertEquals(username, userDetails.getUsername());
    verify(userRepository).findByUsername(username);
  }

  @Test
  void loadUserByUsername_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
    // given
    String username = "nonexistent";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    // when && then
    assertThrows(UserNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
    verify(userRepository).findByUsername(username);
  }
}
