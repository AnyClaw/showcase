package com.example.showcase.mapper;

import com.example.showcase.dto.UserDTO;
import com.example.showcase.entity.User;

public class UserMapper {

    private UserMapper(){}

    public static UserDTO entityToDto(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getMiddleName(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getRole().getName()
        );
    }
}
