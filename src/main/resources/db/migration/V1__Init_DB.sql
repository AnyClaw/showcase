CREATE TABLE IF NOT EXISTS public.statuses (
    status_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    status_name VARCHAR(100) NOT NULL,
    CONSTRAINT statuses_pkey PRIMARY KEY (status_id),
    CONSTRAINT statuses_status_name_key UNIQUE (status_name)
);

CREATE TABLE IF NOT EXISTS public.roles (
    role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    role_name VARCHAR(100) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (role_id),
    CONSTRAINT roles_role_name_key UNIQUE (role_name)
);

CREATE TABLE IF NOT EXISTS public.teams (
    team_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    team_name VARCHAR(100) NOT NULL,
    leader INTEGER,
    CONSTRAINT teams_pkey PRIMARY KEY (team_id),
    CONSTRAINT teams_team_name_key UNIQUE (team_name)
);

CREATE TABLE IF NOT EXISTS public.users (
    user_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role_id INTEGER NOT NULL DEFAULT 1,
    team_id INTEGER,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(role_id),
    CONSTRAINT users_team_id_fkey FOREIGN KEY (team_id) REFERENCES public.teams(team_id)
);

ALTER TABLE public.teams
    ADD CONSTRAINT teams_leader_fkey FOREIGN KEY (leader) REFERENCES public.users(user_id);

CREATE TABLE IF NOT EXISTS public.projects (
    project_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    title VARCHAR(200) NOT NULL,
    owner_id INTEGER not null references users(user_id),
    target TEXT NOT NULL,
    barrier TEXT NOT NULL,
    existing_solution TEXT NOT NULL,
    project_type VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    project_status_id INTEGER NOT NULL DEFAULT 1,
    CONSTRAINT projects_pkey PRIMARY KEY (project_id),
    CONSTRAINT projects_project_status_fkey FOREIGN KEY (project_status_id) REFERENCES public.statuses(status_id)
);

CREATE TABLE IF NOT EXISTS public.works (
    work_project_id INTEGER NOT NULL,
    work_team_id INTEGER NOT NULL,
    work_status INTEGER NOT NULL DEFAULT 1,
    CONSTRAINT work_pk PRIMARY KEY (work_project_id, work_team_id),
    CONSTRAINT work_project_id_fk FOREIGN KEY (work_project_id) REFERENCES public.projects(project_id) ON DELETE RESTRICT,
    CONSTRAINT work_team_id_fk FOREIGN KEY (work_team_id) REFERENCES public.teams(team_id) ON DELETE RESTRICT,
    CONSTRAINT works_work_status_fkey FOREIGN KEY (work_status) REFERENCES public.statuses(status_id)
);

insert into roles(role_name) values ('STUDENT'), ('CUSTOMER'), ('ADMINISTRATOR');
insert into statuses(status_name) values ('ON_VERIFICATION'), ('ON_WORK'), ('COMPLETED'), ('FREE'), ('CANCELED');