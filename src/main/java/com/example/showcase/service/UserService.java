package com.example.showcase.service;

import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.dto.response.UserBriefDTO;
import com.example.showcase.dto.response.UserResponseDTO;
import com.example.showcase.entity.User;
import com.example.showcase.exception.UserNotFoundException;
import com.example.showcase.mapper.UserMapper;
import com.example.showcase.repository.ProjectsRepository;
import com.example.showcase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectsRepository projectRepository;

    public Iterable<UserResponseDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false)
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDTO getById(int id) {
        return userMapper.toDto(userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserByEmail(String email) {
        User user = userRepository. findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // возвращаем только нужные поля, остальные — null
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                null,               // phoneNumber не возвращаем
                user.getEmail(),
                null                            // role не возвращаем
        );
    }

    public List<ProjectBriefDTO> getUserProjectHistory(
            Integer userId,
            String department,
            String projectType,
            String title) {
        if (userId == null) {
            throw new UserNotFoundException("User not authenticated");
        }
        String cleanDepartment = (department != null && !department.isBlank()) ? department : null;
        String cleanProjectType = (projectType != null && !projectType.isBlank()) ? projectType : null;
        String cleanTitle = (title != null && !title.isBlank()) ? title : null;

        return projectRepository.findUserProjectHistory(userId, cleanDepartment, cleanProjectType, cleanTitle);
    }

    public List<UserBriefDTO> getAllUsersForAdmin(String role, Integer groupId, String fullName, String email) {
        String cleanRole = (role != null && !role.isBlank()) ? role : null;
        String cleanFullName = (fullName != null && !fullName.isBlank()) ? fullName : null;
        String cleanEmail = (email != null && !email.isBlank()) ? email : null;

        return userRepository.findAllUsersForAdmin(cleanRole, groupId, cleanFullName, cleanEmail);
    }
}
