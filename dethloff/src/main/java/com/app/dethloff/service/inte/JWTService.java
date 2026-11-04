package com.app.dethloff.service.inte;

import com.app.dethloff.model.Role;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

  String generateToken(String username, Role role);

  String extractUsername(String token);

  boolean validateToken(String token, UserDetails userDetails);
}
