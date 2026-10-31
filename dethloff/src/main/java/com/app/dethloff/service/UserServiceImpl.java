package com.app.dethloff.service;

import com.app.dethloff.repository.UserRepository;
import com.app.dethloff.exceptions.model.InvalidLoginOrPasswordException;
import com.app.dethloff.exceptions.model.InvalidPasswordException;
import com.app.dethloff.exceptions.model.UserNotFoundException;
import com.app.dethloff.exceptions.model.UsernameTakenException;
import com.app.dethloff.model.UserEntity;
import com.app.dethloff.rest.DTO.LoginRequestDTO;
import com.app.dethloff.rest.DTO.RegisterRequestDTO;
import com.app.dethloff.rest.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final AuthenticationManager auth;

    private final JWTService jwtService;

    private final PasswordService passwordService;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager auth, JWTServiceImpl jwtService, PasswordService passwordService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.auth = auth;
        this.jwtService = jwtService;
        this.passwordService = passwordService;
        this.userMapper = userMapper;
    }

    private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder(12);

    public void register(RegisterRequestDTO request) {
        UserEntity userEntity = userMapper.registerDTOToEntity(request);
        Optional<UserEntity> existingUser = userRepository.findByUsername(userEntity.getUsername());
        if(existingUser.isPresent()) {
            throw new UsernameTakenException("this username is already taken");
        }

        if(passwordService.validateNewPassword(userEntity.getPassword())) {
            userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        } else {
            throw new InvalidPasswordException("given password doesn't meet the requirements");
        }

        userRepository.save(userEntity);
    }

    public String verify(LoginRequestDTO request) {
        try {
            Authentication authentication = auth.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            if(authentication.isAuthenticated()) {
                UserEntity existingUserEntity = userRepository.findByUsername(request.username())
                        .orElseThrow(() -> new UserNotFoundException("No user with username: " + request.username()));
                return jwtService.generateToken(existingUserEntity.getUsername(), existingUserEntity.getRole());
            }
        } catch (RuntimeException exc) {
            throw new InvalidLoginOrPasswordException("invalid username or password");
        }




        return "login failed";
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        UserEntity existingUserEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No user with username: " + username));

        Authentication authentication = auth.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

        if(!authentication.isAuthenticated()) {
            throw new InvalidPasswordException("Bad password");
        }

        if(passwordService.validateNewPassword(newPassword)) {
            existingUserEntity.setPassword(encoder.encode(newPassword));
        } else {
            throw new InvalidPasswordException("given password doesn't meet the requirements");
        }

        userRepository.save(existingUserEntity);
    }
}
