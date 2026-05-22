package com.example.showcase.repository;

import com.example.showcase.dto.response.TeamQueryResult;
import com.example.showcase.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByMembersId(Integer userId);
    // тоже самое почти но с косвенной проверкой существования в принципе пары
    @Query(value = "SELECT COUNT(*) FROM team_members WHERE team_id = :teamId AND user_id = :userId AND left_at IS NOT NULL", nativeQuery = true)
    long countMemberInTeam(@Param("teamId") int teamId, @Param("userId") int userId);
    // без дубликатов мини джойн без полной связки
    // проверка является ли пользователь лидером
    @Query(value = "SELECT EXISTS(SELECT 1 FROM team_members WHERE team_id = :teamId AND user_id = :userId AND is_leader = true)", nativeQuery = true)
    boolean isUserLeader(@Param("teamId") int teamId, @Param("userId") int userId);
    // проверяет состоит ли пользователь ВООБЩЕ в какой-либо команде
    @Query(value = "SELECT EXISTS(SELECT 1 FROM team_members WHERE user_id = :userId)", nativeQuery = true)
    boolean isUserInAnyTeam(@Param("userId") int userId);

    // добавить участника
    @Modifying
    @Query(value = "INSERT INTO team_members (team_id, user_id) VALUES (:teamId, :userId)", nativeQuery = true)
    void addMemberToTeam(@Param("teamId") int teamId, @Param("userId") int userId);




    @Query(value = """
        SELECT COUNT(*) FROM team_members 
        WHERE team_id = :teamId AND left_at IS NULL
        """, nativeQuery = true)
    int countActiveMembersByTeamId(@Param("teamId") Integer teamId);

    @Query(value = """
        SELECT is_leader FROM team_members 
        WHERE team_id = :teamId AND user_id = :userId AND left_at IS NULL
        """, nativeQuery = true)
    Optional<Boolean> isUserActiveLeader(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    @Query(value = """
        UPDATE team_members SET is_leader = false 
        WHERE team_id = :teamId AND is_leader = true AND left_at IS NULL
        """, nativeQuery = true)
    @Modifying
    void clearActiveLeader(@Param("teamId") Integer teamId);

    @Query(value = """
        UPDATE team_members SET is_leader = true 
        WHERE team_id = :teamId AND user_id = :newLeaderId AND left_at IS NULL
        """, nativeQuery = true)
    @Modifying
    void setLeaderForTeam(@Param("teamId") Integer teamId, @Param("newLeaderId") Integer newLeaderId);

    @Query(value = """
        UPDATE team_members SET left_at = CURRENT_TIMESTAMP 
        WHERE team_id = :teamId AND user_id = :userId AND left_at IS NULL
        """, nativeQuery = true)
    @Modifying
    void markMemberAsLeft(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    @Query(value = """
        SELECT EXISTS(
            SELECT 1 FROM team_members 
            WHERE team_id = :teamId AND user_id = :userId AND left_at IS NULL
        )
        """, nativeQuery = true)
    boolean isUserActiveMember(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    @Query(value = """
        SELECT 
            t.team_id AS teamId, t.team_name AS teamName,
            u.user_id AS userId, u.first_name AS firstName, u.last_name AS lastName, u.email AS email,
            tm.is_leader AS isLeader, tm.joined_at AS joinedAt, tm.left_at AS leftAt
        FROM team_members tm
        JOIN teams t ON t.team_id = tm.team_id
        JOIN users u ON u.user_id = tm.user_id
        WHERE tm.team_id = :teamId AND tm.left_at IS NULL
        ORDER BY tm.is_leader DESC, tm.joined_at ASC
        """, nativeQuery = true)
    List<TeamQueryResult> findActiveMembersByTeamId(@Param("teamId") Integer teamId);

    @Query(value = """
    SELECT team_id 
    FROM team_members 
    WHERE user_id = :userId AND left_at IS NULL
    LIMIT 1
    """, nativeQuery = true)
    Optional<Integer> findCurrentTeamIdByUserId(@Param("userId") Integer userId);

    @Query(value = """
        SELECT EXISTS(
            SELECT 1 FROM team_members 
            WHERE team_id = :teamId AND user_id = :targetId 
            AND is_leader = false AND left_at IS NULL
        )
        """, nativeQuery = true)
    boolean isUserActiveRegularMember(@Param("teamId") Integer teamId, @Param("targetId") Integer targetId);

    @Modifying
    @Query(value = """
        INSERT INTO team_members (team_id, user_id, is_leader, joined_at, left_at)
        VALUES (:teamId, :userId, false, CURRENT_TIMESTAMP, NULL)
        ON CONFLICT (team_id, user_id) DO UPDATE 
        SET left_at = NULL, joined_at = CURRENT_TIMESTAMP, is_leader = false
        WHERE team_members.left_at IS NOT NULL
        """, nativeQuery = true)
    void addActiveMember(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    @Query(value = """
        SELECT team_id FROM team_members 
        WHERE user_id = :userId AND is_leader = true AND left_at IS NULL
        LIMIT 1
        """, nativeQuery = true)
    Optional<Integer> findActiveTeamIdByLeaderId(@Param("userId") Integer userId);

    @Query(value = "SELECT name FROM teams WHERE id = :teamId", nativeQuery = true)
    Optional<String> findTeamNameById(@Param("teamId") Integer teamId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE user_id = :userId)", nativeQuery = true)
    boolean userExists(@Param("userId") Integer userId);


}
