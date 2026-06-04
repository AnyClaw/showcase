package com.example.showcase.repository;

import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.entity.Project;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends
        CrudRepository<Project, Integer>,
        JpaSpecificationExecutor<Project>
{
    @Query(value = """
        SELECT DISTINCT
            p.project_id AS projectId,
            p.title AS title,
            p.target AS target,
            p.department AS department,
            p.project_type AS projectType,
            p.project_status AS projectStatus
        FROM projects p
        JOIN project_stages ps ON p.project_id = ps.project_id
        WHERE ps.team_id = :teamId
          AND (:department IS NULL OR LOWER(p.department) = LOWER(:department))
          AND (:projectType IS NULL OR LOWER(p.project_type) = LOWER(:projectType))
          AND (:status IS NULL OR p.project_status = :status)
          AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
        """, nativeQuery = true)
    List<ProjectBriefDTO> findProjectsBriefByTeamId(
            @Param("teamId") Integer teamId,
            @Param("department") String department,
            @Param("projectType") String projectType,
            @Param("status") String status,
            @Param("title") String title
    );

    @Query(value = """
        SELECT DISTINCT
            p.project_id AS projectId,
            p.title AS title,
            p.target AS target, 
            p.department AS department,
            p.project_type AS projectType,
            p.project_status AS projectStatus
        FROM users u
        JOIN team_members tm ON u.user_id = tm.user_id
        JOIN project_stages ps ON tm.team_id = ps.team_id
        JOIN projects p ON ps.project_id = p.project_id
        WHERE u.user_id = :userId
          AND (:department IS NULL OR LOWER(p.department) = LOWER(:department))
          AND (:projectType IS NULL OR LOWER(p.project_type) = LOWER(:projectType))
          AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
        """, nativeQuery = true)
    List<ProjectBriefDTO> findUserProjectHistory(
            @Param("userId") Integer userId,
            @Param("department") String department,
            @Param("projectType") String projectType,
            @Param("title") String title
    );

    @Query(value = """
            SELECT 
                p.project_id AS projectId,
                p.title AS title,
                p.target AS target,
                p.department AS department,
                p.project_type AS projectType,
                p.project_status AS projectStatus
            FROM projects p
            WHERE p.user_id = :userId
              AND (:department IS NULL OR LOWER(p.department) = LOWER(:department))
              AND (:projectType IS NULL OR LOWER(p.project_type) = LOWER(:projectType))
              AND (:status IS NULL OR p.project_status = :status)
              AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
            ORDER BY p.project_id DESC
            """, nativeQuery = true)
    List<ProjectBriefDTO> findProjectsByOwnerId(
            @Param("userId") Integer userId,
            @Param("department") String department,
            @Param("projectType") String projectType,
            @Param("status") String status,
            @Param("title") String title
    );

    @Query(value = """
            SELECT DISTINCT
                p.project_id AS projectId,
                p.title AS title,
                p.target AS target,
                p.department AS department,
                p.project_type AS projectType,
                p.project_status AS projectStatus
            FROM projects p
            JOIN project_stages ps ON p.project_id = ps.project_id
            JOIN team_members tm ON ps.team_id = tm.team_id
            JOIN users u ON tm.user_id = u.user_id
            JOIN groups g ON u.group_id = g.group_id
            WHERE g.user_id = :teacherId          
              AND tm.left_at IS NULL              
              AND (:department IS NULL OR LOWER(p.department) = LOWER(:department))
              AND (:projectType IS NULL OR LOWER(p.project_type) = LOWER(:projectType))
              AND (:status IS NULL OR p.project_status = :status)
              AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
              AND (:groupId IS NULL OR g.group_id = :groupId) 
              
            ORDER BY p.project_id DESC
            """, nativeQuery = true)
    List<ProjectBriefDTO> findProjectsByTeacherGroup(
            @Param("teacherId") Integer teacherId,
            @Param("department") String department,
            @Param("projectType") String projectType,
            @Param("status") String status,
            @Param("title") String title,
            @Param("groupId") Integer groupId
    );
}


