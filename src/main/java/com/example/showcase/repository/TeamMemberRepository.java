package com.example.showcase.repository;

import com.example.showcase.entity.TeamMember;
import com.example.showcase.entity.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {

//    // Найти активное членство пользователя (где left_at = null)
//    Optional<TeamMember> findByUserIdAndLeftAtIsNull(Integer userId);
//
//    // Найти всех активных участников команды
//    List<TeamMember> findByTeamIdAndLeftAtIsNull(Integer teamId);
//
//    // Проверить, является ли пользователь лидером
//    boolean existsByUserIdAndIsLeaderTrueAndLeftAtIsNull(Integer userId);
}