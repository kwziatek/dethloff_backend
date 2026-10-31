package com.app.dethloff.service;

import com.app.dethloff.repository.UserRepository;
import com.app.dethloff.exceptions.model.UserNotFoundException;
import com.app.dethloff.model.UserPrincipal;
import com.app.dethloff.model.UserEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = repo.findByUsername(username);

        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return new UserPrincipal(user.get());
    }
}
