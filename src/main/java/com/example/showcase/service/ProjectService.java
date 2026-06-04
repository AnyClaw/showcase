package com.example.showcase.service;

import com.example.showcase.dto.request.ProjectCreateRequestDTO;
import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.PageResponse;
import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.dto.response.ProjectResponseDTO;
import com.example.showcase.entity.Project;
import com.example.showcase.entity.User;
import com.example.showcase.enums.ProjectStatus;
import com.example.showcase.exception.ProjectNotFoundException;
import com.example.showcase.exception.UserNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public ProjectResponseDTO createProject(ProjectCreateRequestDTO request, Integer creatorId) {
        User owner = userRepository.findById(creatorId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + creatorId + " не найден"));

        Project project = Project.builder()
                .title(request.title())
                .projectType(request.projectType().name())
                .department(request.department().name())
                .target(request.target())
                .barrier(request.barrier())
                .existingSolution(request.existingSolution())
                .status(ProjectStatus.ON_VERIFICATION)
                .owner(owner)
                .build();

        Project savedProject = projectsRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    public List<ProjectBriefDTO> getMyProjectsAsClient(
            Integer userId,
            String department,
            String projectType,
            ProjectStatus status,
            String title) {

        if (userId == null) {
            throw new UserNotFoundException("ID пользователя не может быть null");
        }

        String cleanDepartment = (department != null && !department.isBlank()) ? department : null;
        String cleanProjectType = (projectType != null && !projectType.isBlank()) ? projectType : null;
        String cleanStatus = (status != null) ? status.name() : null; // Превращаем Enum в строку для БД
        String cleanTitle = (title != null && !title.isBlank()) ? title : null;

        return projectsRepository.findProjectsByOwnerId(
                userId,
                cleanDepartment,
                cleanProjectType,
                cleanStatus,
                cleanTitle
        );
    }

    public List<ProjectBriefDTO> getProjectsOfMyGroup(
            Integer teacherId,
            String department,
            String projectType,
            ProjectStatus status,
            String title,
            Integer groupId) {
        if (teacherId == null) {
            throw new UserNotFoundException("Передан null userID)");
        }

        String cleanDepartment = (department != null && !department.isBlank()) ? department : null;
        String cleanProjectType = (projectType != null && !projectType.isBlank()) ? projectType : null;
        String cleanStatus = (status != null) ? status.name() : null;
        String cleanTitle = (title != null && !title.isBlank()) ? title : null;

        return projectsRepository.findProjectsByTeacherGroup(
                teacherId,
                cleanDepartment,
                cleanProjectType,
                cleanStatus,
                cleanTitle,
                groupId
        );
    }

    public List<ProjectBriefDTO> getAllProjectsForAdmin(
            String department,
            String projectType,
            ProjectStatus status,
            String title,
            Integer groupId,
            Integer teamId) {

        String cleanDepartment = (department != null && !department.isBlank()) ? department : null;
        String cleanProjectType = (projectType != null && !projectType.isBlank()) ? projectType : null;
        String cleanStatus = (status != null) ? status.name() : null;
        String cleanTitle = (title != null && !title.isBlank()) ? title : null;

        return projectsRepository.findAllProjectsForAdmin(
                cleanDepartment,
                cleanProjectType,
                cleanStatus,
                cleanTitle,
                groupId,
                teamId
        );
    }
}
