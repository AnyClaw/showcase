package com.example.showcase.mapper;

import com.example.showcase.dto.response.UserResponseDTO;
import com.example.showcase.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(source = "role.toString()", target = "role")
    UserResponseDTO toDto(User user);
}
