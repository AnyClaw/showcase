package com.example.showcase.repository;

import com.example.showcase.dto.response.GroupBriefDTO;
import com.example.showcase.dto.response.GroupStudentResult;
import com.example.showcase.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query(value = """
            SELECT 
                group_id AS id, 
                group_name AS name 
            FROM groups 
            WHERE user_id = :teacherId
            """, nativeQuery = true)
    List<GroupBriefDTO> findGroupsByTeacherId(@Param("teacherId") Integer teacherId);

    @Query(value = """
            SELECT 
                g.group_id AS groupId,
                g.group_name AS groupName,
                u.user_id AS studentId,
                u.first_name AS firstName,
                u.last_name AS lastName,
                u.middle_name AS middleName,
                u.email AS email
            FROM groups g
            LEFT JOIN users u ON g.group_id = u.group_id
            WHERE g.user_id = :teacherId
              AND (:groupName IS NULL OR LOWER(g.group_name) LIKE LOWER(CONCAT('%', :groupName, '%')))
            ORDER BY g.group_id, u.last_name, u.first_name
            """, nativeQuery = true)
    List<GroupStudentResult> findGroupsWithStudentsByTeacherId(
            @Param("teacherId") Integer teacherId,
            @Param("groupName") String groupName // <-- Новый параметр
    );

}

