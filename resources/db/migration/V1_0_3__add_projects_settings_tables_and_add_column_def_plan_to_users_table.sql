CREATE TABLE manticore.settings_stash_projects
(
    id           SERIAL,
    project_key  VARCHAR(25)  NOT NULL,
    project_name VARCHAR(45) NOT NULL,
    repo_name    VARCHAR(60)  NOT NULL UNIQUE,
    CONSTRAINT settings_stash_projects_pk PRIMARY KEY (id)
);

CREATE TABLE manticore.settings_bamboo_projects
(
    id           SERIAL,
    project_key  VARCHAR(60)  NOT NULL,
    project_name VARCHAR(80) NOT NULL,
    plan_name    VARCHAR(80)  NOT NULL UNIQUE,
    CONSTRAINT settings_bamboo_projects_pk PRIMARY KEY (id)
);

ALTER TABLE manticore.users ADD default_plan VARCHAR(60) DEFAULT 'PANBUILD-PANBETMASTER'
