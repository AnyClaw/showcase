package com.example.showcase.service;

import com.example.showcase.dto.request.ProjectRequestDTO;
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

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectsRepository projectsRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectResponseDTO> getAllProjects() {
        Iterable<Project> projects = projectsRepository.findAll();

        return StreamSupport.stream(projects.spliterator(), false)
                .map(projectMapper::toDto)
                .toList();
    }

    public ProjectResponseDTO getById(int id) {
        return projectMapper.toDto(projectsRepository
                .findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id))
        );
    }

    // тестовый метод, потом удалить
    public ProjectResponseDTO addProject(ProjectRequestDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        log.info("Результат маппинга из дто в сущность: {}", project);

        // Получение информации об аутентифицированном пользователе, обязательно для защищенных эндпоинтов
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }

        // в getName() хранится уникальный идентификатор пользователя (не id), в нашем случае это email
        String email = authentication.getName();
        log.info("Полученное из токена имя пользователя: {}", email);

        User owner = userRepository.findByEmail(email).orElseThrow();
        project.setOwner(owner);
        project.setStatus(ProjectStatus.ON_VERIFICATION);

        return projectMapper.toDto(projectsRepository.save(project));
    }

    public Iterable<ProjectResponseDTO> findProjects(
            Integer page, Integer size, ProjectStatus projectStatus,
            String projectType, String department, String title, Integer teamId
    ) {
        log.info(
                "{}, {}, {}, {}, {}, {}, {}",
                page, size, projectStatus, projectType, department, title, teamId
        );

        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<Project> specification = ProjectSpecification.buildFilter(
                projectStatus, projectType, department, title
        );
        Page<Project> response = projectsRepository.findAll(specification, pageRequest);

        return response.getContent().stream()
                .map(projectMapper::toDto)
                .toList();
    }
}
