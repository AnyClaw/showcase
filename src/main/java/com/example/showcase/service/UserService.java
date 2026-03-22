package com.example.showcase.service;

import com.example.showcase.dto.UserDTO;
import com.example.showcase.entity.User;
import com.example.showcase.exception.UserNotFoundException;
import com.example.showcase.mapper.UserMapper;
import com.example.showcase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Iterable<UserDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false)
                .map(UserMapper::entityToDto)
                .toList();
    }

    public UserDTO getById(int id) {
        return UserMapper.entityToDto(userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
        );
    }
}
