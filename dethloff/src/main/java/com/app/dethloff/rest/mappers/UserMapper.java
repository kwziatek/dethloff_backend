package com.app.dethloff.rest.mappers;

import com.app.dethloff.rest.DTO.RegisterRequestDTO;
import com.app.dethloff.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity registerDTOToEntity(RegisterRequestDTO register) {
        return UserEntity.builder()
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
