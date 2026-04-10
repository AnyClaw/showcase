CREATE TABLE statuses (
    status_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    status_name VARCHAR(100) constraint uq_status_name unique NOT null
);

CREATE TABLE roles (
    role_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    role_name VARCHAR(100) constraint uq_role_name unique NOT null

);

CREATE TABLE teams (
    team_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    team_name VARCHAR(100) constraint uq_team_name  UNIQUE NOT null,
    is_team_active BOOLEAN not null default true
);

CREATE TABLE users (
    user_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) not null,
    email VARCHAR(100) constraint uq_user_email unique NOT NULL,
    role_id int NOT NULL default 1,

    constraint fk_users_role_id foreign key (role_id) REFERENCES roles(role_id) on delete restrict
);

CREATE TABLE groups (
    group_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id int not null ,
    group_name VARCHAR (100) not null,
    is_group_active BOOLEAN not null default true,

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
    is_leader boolean not null default false,
    joined_at TIMESTAMPTZ not null default CURRENT_TIMESTAMP,
    left_at TIMESTAMPTZ ,


    PRIMARY KEY (team_id, user_id),
    CONSTRAINT fk_team_members_user_id FOREIGN KEY (user_id) REFERENCES users(user_id ) on delete restrict,
    CONSTRAINT fk_team_members_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id )  on delete restrict,
    constraint chk_team_memebers_dates check (left_at is null or left_at >= joined_at)

);

create unique index one_leader_per_team
    on team_members(team_id)
    where is_leader = true and left_at is null;

CREATE TABLE projects (
    project_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id int,
    status_id int NOT NULL default 1,
    title VARCHAR(254) NOT NULL,
    target TEXT NOT NULL,
    barrier TEXT NOT NULL,
    existing_solution TEXT NOT NULL,
    project_type VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT null,

    CONSTRAINT fk_projects_status_id FOREIGN KEY (status_id) REFERENCES statuses(status_id)  on delete restrict,
    CONSTRAINT fk_projects_user_id FOREIGN KEY (user_id) REFERENCES users(user_id )  on delete restrict
);

CREATE TABLE project_stages (
    stage_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    project_id int NOT NULL,
    team_id int NOT NULL,
    status_id int NOT NULL default 1,
    start_time TIMESTAMPTZ not null default CURRENT_TIMESTAMP,
    end_time TIMESTAMPTZ ,

    CONSTRAINT fk_project_stagees_status_id FOREIGN KEY (status_id) REFERENCES statuses(status_id)  on delete restrict,
    CONSTRAINT fk_stage_project_id FOREIGN KEY (project_id) REFERENCES projects(project_id ) on delete RESTRICT,
    CONSTRAINT fk_stage_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id )  on delete restrict,
    constraint chk_tproject_stages_dates check (end_time is null or end_time >= start_time)
);

INSERT INTO statuses (status_name)
VALUES
    ('ON_VERIFICATION'),
    ('REJECTED'),
    ('AVAILABLE'),
    ('COMPLETED'),
    ('IN_PROGRESS');

INSERT INTO roles (role_name)
VALUES
    ('ADMINISTRATOR'),
    ('STUDENT'),
    ('CLIENT'),
    ('TEACHER');