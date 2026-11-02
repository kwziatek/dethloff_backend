package com.app.dethloff.service;

import com.app.dethloff.exceptions.model.InvalidLoginOrPasswordException;
import com.app.dethloff.exceptions.model.InvalidPasswordException;
import com.app.dethloff.exceptions.model.UserNotFoundException;
import com.app.dethloff.exceptions.model.UsernameTakenException;
import com.app.dethloff.model.UserEntity;
import com.app.dethloff.repository.UserRepository;
import com.app.dethloff.rest.DTO.LoginRequestDTO;
import com.app.dethloff.rest.DTO.RegisterRequestDTO;
import com.app.dethloff.rest.mappers.UserMapper;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final AuthenticationManager auth;

  private final JWTService jwtService;

  private final PasswordService passwordService;

  private final UserMapper userMapper;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      AuthenticationManager auth,
      JWTServiceImpl jwtService,
      PasswordService passwordService,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.auth = auth;
    this.jwtService = jwtService;
    this.passwordService = passwordService;
    this.userMapper = userMapper;
  }

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public void register(RegisterRequestDTO request) {
    UserEntity userEntity = userMapper.registerDTOToEntity(request);
    Optional<UserEntity> existingUser = userRepository.findByUsername(userEntity.getUsername());
    if (existingUser.isPresent()) {
      throw new UsernameTakenException();
    }

    if (passwordService.validateNewPassword(userEntity.getPassword())) {
      userEntity.setPassword(encoder.encode(userEntity.getPassword()));
    } else {
      throw new InvalidPasswordException();
    }

    userRepository.save(userEntity);
  }

  public String verify(LoginRequestDTO request) {
    try {
      Authentication authentication =
          auth.authenticate(
              new UsernamePasswordAuthenticationToken(request.username(), request.password()));
      if (authentication.isAuthenticated()) {
        UserEntity existingUserEntity =
            userRepository
                .findByUsername(request.username())
                .orElseThrow(UserNotFoundException::new);
        return jwtService.generateToken(
            existingUserEntity.getUsername(), existingUserEntity.getRole());
      }
    } catch (RuntimeException exc) {
      throw new InvalidLoginOrPasswordException();
    }

    return "login failed";
  }

  @Override
  public void changePassword(String username, String oldPassword, String newPassword) {
    UserEntity existingUserEntity =
        userRepository
            .findByUsername(username)
            .orElseThrow(UserNotFoundException::new);

    Authentication authentication =
        auth.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

    if (!authentication.isAuthenticated()) {
      throw new InvalidPasswordException("WRONG_PASSWORD");
    }

    if (passwordService.validateNewPassword(newPassword)) {
      existingUserEntity.setPassword(encoder.encode(newPassword));
    } else {
      throw new InvalidPasswordException();
    }

    userRepository.save(existingUserEntity);
  }
}
