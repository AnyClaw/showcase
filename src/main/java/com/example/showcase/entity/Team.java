package com.example.showcase.entity;

import com.example.showcase.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer id;

    @Column(name = "team_name", unique = true, nullable = false)
    private String name;

    // ⭐ Самая важная часть: связь с пользователями через team_members
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_members",           // таблица-связка в БД
            joinColumns = @JoinColumn(name = "team_id"),      // колонка для команды
            inverseJoinColumns = @JoinColumn(name = "user_id") // колонка для пользователя
    )
    private List<User> members;
}

