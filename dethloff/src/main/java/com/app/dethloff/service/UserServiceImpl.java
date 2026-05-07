package com.app.dethloff.service;

import com.app.dethloff.dao.UserRepo;
import com.app.dethloff.exceptions.model.InvalidLoginOrPasswordException;
import com.app.dethloff.exceptions.model.InvalidPasswordException;
import com.app.dethloff.exceptions.model.UserNotFoundException;
import com.app.dethloff.exceptions.model.UsernameTakenException;
import com.app.dethloff.model.DTO.LoginRequest;
import com.app.dethloff.model.DTO.RegisterRequest;
import com.app.dethloff.model.DTO.mappers.UserMapper;
import com.app.dethloff.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    private final AuthenticationManager auth;

    private final JWTService jwtService;

    private final PasswordService passwordService;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, AuthenticationManager auth, JWTServiceImpl jwtService, PasswordService passwordService, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.auth = auth;
        this.jwtService = jwtService;
        this.passwordService = passwordService;
        this.userMapper = userMapper;
    }

    private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder(12);

    public void register(RegisterRequest request) {
        User user = userMapper.registerDTOToEntity(request);
        Optional<User> existingUser = userRepo.findByUsername(user.getUsername());
        if(existingUser.isPresent()) {
            throw new UsernameTakenException("this username is already taken");
        }

        if(passwordService.validateNewPassword(user.getPassword())) {
            user.setPassword(encoder.encode(user.getPassword()));
        } else {
            throw new InvalidPasswordException("given password doesn't meet the requirements");
        }

        userRepo.save(user);
    }

    public String verify(LoginRequest request) {
        try {
            Authentication authentication = auth.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            if(authentication.isAuthenticated()) {
                User existingUser = userRepo.findByUsername(request.username())
                        .orElseThrow(() -> new UserNotFoundException("No user with username: " + request.username()));
                return jwtService.generateToken(existingUser.getUsername(), existingUser.getRole());
            }
        } catch (RuntimeException exc) {
            throw new InvalidLoginOrPasswordException("invalid username or password");
        }




        return "login failed";
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No user with username: " + username));

        Authentication authentication = auth.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

        if(!authentication.isAuthenticated()) {
            throw new InvalidPasswordException("Bad password");
        }

        if(passwordService.validateNewPassword(newPassword)) {
            existingUser.setPassword(encoder.encode(newPassword));
        } else {
            throw new InvalidPasswordException("given password doesn't meet the requirements");
        }

        userRepo.save(existingUser);
    }
}
