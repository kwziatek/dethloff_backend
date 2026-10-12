package com.app.dethloff.model.DTO.mappers;

import com.app.dethloff.model.DTO.RegisterRequestDTO;
import com.app.dethloff.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User registerDTOToEntity(RegisterRequestDTO register) {
        return User.builder()
                .id(null)
                .username(register.username())
                .password(register.password())
                .role(register.role())
                .build();
    }

//    public User loginDTOToEntity(LoginRequest login) {
//        return User.builder().build();
//    }
}
