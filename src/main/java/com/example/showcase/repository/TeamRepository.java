package com.example.showcase.repository;

import com.example.showcase.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // найти Team, у которого в членах (members) есть User с таким id
    Optional<Team> findByMembersId(Integer userId);
    // вывести адекватно полную инфу о команде
    @Query(value = """
        SELECT t.team_id, t.team_name, u.user_id, u.first_name, u.last_name, u.email, tm.is_leader
        FROM team_members tm
        JOIN teams t ON t.team_id = tm.team_id
        JOIN users u ON u.user_id = tm.user_id
        WHERE t.team_id = (SELECT team_id FROM team_members WHERE user_id = :userId)
        """, nativeQuery = true)
    List<Object[]> getFullTeamData(@Param("userId") Integer userId);
    // сколько участников в команде
    @Query("SELECT COUNT(m) FROM Team t JOIN t.members m WHERE t.id = :teamId")
    int countMembersByTeamId(@Param("teamId") int teamId);
    // тоже самое почти но с косвенной проверкой существования в принципе пары
    @Query(value = "SELECT COUNT(*) FROM team_members WHERE team_id = :teamId AND user_id = :userId", nativeQuery = true)
    long countMemberInTeam(@Param("teamId") int teamId, @Param("userId") int userId);
    // без дубликатов мини джойн без полной связки
    @Query("SELECT DISTINCT t FROM Team t JOIN FETCH t.members m WHERE m.id = :userId")
    Optional<Team> findWithMembersByUserId(@Param("userId") int userId);
    // проверка является ли пользователь лидером
    @Query(value = "SELECT EXISTS(SELECT 1 FROM team_members WHERE team_id = :teamId AND user_id = :userId AND is_leader = true)", nativeQuery = true)
    boolean isUserLeader(@Param("teamId") int teamId, @Param("userId") int userId);
    // проверяет состоит ли пользователь ВООБЩЕ в какой-либо команде
    @Query(value = "SELECT EXISTS(SELECT 1 FROM team_members WHERE user_id = :userId)", nativeQuery = true)
    boolean isUserInAnyTeam(@Param("userId") int userId);
    // убрать лидера перед назначением нового для того чтобы обойти ограничение
    @Modifying
    @Query(value = "UPDATE team_members SET is_leader = false WHERE team_id = :teamId", nativeQuery = true)
    void clearLeaderForTeam(@Param("teamId")Integer teamId);
    // назначить лидера
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE team_members SET is_leader = true WHERE team_id = :teamId AND user_id = :newLeaderId", nativeQuery = true)
    void setLeaderForTeam(@Param("teamId") Integer  teamId, @Param("newLeaderId") Integer  newLeaderId);
    // удалить участника
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM team_members WHERE team_id = :teamId AND user_id = :userId", nativeQuery = true)
    void removeMemberFromTeam(@Param("teamId") Integer  teamId, @Param("userId") Integer  userId);
    // добавить участника
    @Modifying
    @Query(value = "INSERT INTO team_members (team_id, user_id) VALUES (:teamId, :userId)", nativeQuery = true)
    void addMemberToTeam(@Param("teamId") int teamId, @Param("userId") int userId);
}
