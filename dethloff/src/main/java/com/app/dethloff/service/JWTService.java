package com.app.dethloff.service;

import com.app.dethloff.login.Role;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String generateToken(String username, Role role);
    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
}
