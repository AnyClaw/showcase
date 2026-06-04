package com.example.showcase.repository;

import com.example.showcase.dto.response.UserBriefDTO;
import com.example.showcase.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(@Param("email") String email);

    @Query(value = """
        SELECT 
            u.user_id AS userId,
            u.first_name AS firstName,
            u.last_name AS lastName,
            u.middle_name AS middleName,
            u.email AS email,
            u.role AS role,
            g.group_id AS groupId,
            g.group_name AS groupName
        FROM users u
        LEFT JOIN groups g ON u.group_id = g.group_id
        WHERE (:role IS NULL OR u.role = :role)
          AND (:groupId IS NULL OR u.group_id = :groupId)
          AND (:fullName IS NULL OR (
              LOWER(u.first_name) LIKE LOWER(CONCAT('%', :fullName, '%'))
              OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', :fullName, '%'))
              OR LOWER(u.middle_name) LIKE LOWER(CONCAT('%', :fullName, '%'))
          ))
          AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
        ORDER BY u.last_name, u.first_name
        """, nativeQuery = true)
    List<UserBriefDTO> findAllUsersForAdmin(
            @Param("role") String role,
            @Param("groupId") Integer groupId,
            @Param("fullName") String fullName,
            @Param("email") String email
    );
}
