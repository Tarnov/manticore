CREATE TABLE IF NOT EXISTS manticore.closed_tasks_statistic
(
    id          SERIAL,
    date        timestamp,
    project_key VARCHAR(50),
    repo_slug   VARCHAR(50),
    total_tasks integer,
    closed_tasks integer,
    CONSTRAINT closed_tasks_statistic_pk PRIMARY KEY (id)
);

