package com.example.showcase.service;

import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.PageResponse;
import com.example.showcase.dto.response.ProjectResponseDTO;
import com.example.showcase.entity.Project;
import com.example.showcase.entity.User;
import com.example.showcase.enums.ProjectStatus;
import com.example.showcase.exception.ProjectNotFoundException;
import com.example.showcase.mapper.ProjectMapper;
import com.example.showcase.repository.ProjectsRepository;
import com.example.showcase.repository.UserRepository;
import com.example.showcase.specification.ProjectSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectsRepository projectsRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectResponseDTO getById(int id) {
        return projectMapper.toDto(projectsRepository
                .findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id))
        );
    }

    public ProjectResponseDTO addProject(ProjectRequestDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }

        String email = authentication.getName();
        log.info("Полученное из токена имя пользователя: {}", email);

        User owner = userRepository.findByEmail(email).orElseThrow();
        project.setOwner(owner);
        project.setStatus(ProjectStatus.ON_VERIFICATION);

        return projectMapper.toDto(projectsRepository.save(project));
    }

    public PageResponse<ProjectResponseDTO> findProjects(
            Integer page, Integer size, ProjectStatus status,
            String type, String department, String title, Integer teamId
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<Project> specification = ProjectSpecification.buildFilter(
                status, type, department, title
        );

        Page<Project> response = projectsRepository.findAll(specification, pageRequest);

        return PageResponse.from(response.map(projectMapper::toDto));
    }
}
