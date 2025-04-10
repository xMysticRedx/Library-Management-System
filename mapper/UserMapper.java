package com.library.management.mapper;

import com.library.management.dto.UserRegisterDto;
import com.library.management.model.Role;
import com.library.management.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Maps registration DTO to User entity.
     * Password is left unencoded and should be handled by the service layer.
     * Default role is USER unless otherwise specified.
     */
    public User fromRegisterDto(UserRegisterDto dto) {
        return new User(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                Role.USER
        );
    }
}