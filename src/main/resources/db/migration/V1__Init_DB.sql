CREATE TABLE teams (
                       team_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       team_name VARCHAR(100) UNIQUE NOT null
);

CREATE TABLE users (
                       user_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       middle_name VARCHAR(100),
                       password VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(20) not null,
                       email VARCHAR(100) unique NOT NULL,
                       role VARCHAR(100) NOT NULL
);

CREATE TABLE groups (
                        group_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        user_id int not null ,
                        group_name VARCHAR (100) not null,

                        constraint fk_groups_user_id foreign key (user_id) references users(user_id) on delete restrict

);

alter table users
    add column group_id int;

alter table users
    add constraint fk_users_group_id
        foreign key (group_id) references groups(group_id) on delete set null;

CREATE TABLE team_members (
                              team_id int not NULL ,
                              user_id int not null,
                              is_leader boolean default false,

                              PRIMARY KEY (team_id, user_id),
                              CONSTRAINT fk_team_members_user_id FOREIGN KEY (user_id) REFERENCES users(user_id ) on delete cascade,
                              CONSTRAINT fk_team_members_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id )  on delete cascade

);

create unique index one_leader_per_team
    on team_members(team_id)
    where is_leader = true;


CREATE TABLE projects (
                          project_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          user_id int,
                          project_status VARCHAR(100) NOT NULL default 'ON_VERIFICATION',
                          title VARCHAR(254) NOT NULL,
                          target TEXT NOT NULL,
                          barrier TEXT NOT NULL,
                          existing_solution TEXT NOT NULL,
                          project_type VARCHAR(100) NOT NULL,
                          department VARCHAR(100) NOT null,


                          CONSTRAINT fk_projects_user_id FOREIGN KEY (user_id) REFERENCES users(user_id )  on delete cascade
);

CREATE TABLE project_stages (
                                stage_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                project_id int NOT NULL,
                                team_id int,
                                stage_status VARCHAR(100) NOT NULL default 'UNKNOWN',
                                start_time TIMESTAMPTZ not null default CURRENT_TIMESTAMP,
                                end_time TIMESTAMPTZ,

                                CONSTRAINT check_stage_time check (end_time is null or end_time >= start_time),
                                CONSTRAINT stage_project_id_fk FOREIGN KEY (project_id) REFERENCES projects(project_id ) on delete RESTRICT,
                                CONSTRAINT stage_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(team_id )  on delete RESTRICT
);