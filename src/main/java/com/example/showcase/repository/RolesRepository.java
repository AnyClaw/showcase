package com.example.showcase.repository;

import com.example.showcase.entity.UserRole;
import com.example.showcase.enums.UserRoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends CrudRepository<UserRole, Integer> {
    Optional<UserRole> findByName(UserRoleName roleName);
}
