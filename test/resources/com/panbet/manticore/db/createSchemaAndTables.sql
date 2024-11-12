CREATE SCHEMA IF NOT EXISTS manticore;

CREATE TABLE IF NOT EXISTS manticore.task_movement_project_leads
(
    id          SERIAL,
    name        VARCHAR(20),
    display_name VARCHAR(100),
    CONSTRAINT task_movement_project_leads_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS manticore.task_movement_projects
(
    id              SERIAL,
    key             VARCHAR(20),
    name            VARCHAR(50),
    project_lead_id integer,
        CONSTRAINT task_movement_projects_pk PRIMARY KEY (id, key),
        CONSTRAINT task_movement_project_lead_fk FOREIGN KEY (project_lead_id)
        REFERENCES manticore.task_movement_project_leads (id)
);