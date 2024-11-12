INSERT INTO manticore.task_movement_project_leads (name, display_name)
VALUES ('foo', 'Foo foo');

INSERT INTO manticore.task_movement_project_leads (name, display_name)
VALUES ('person', 'Somebody');

INSERT INTO manticore.task_movement_projects (key, name, project_lead_id)
VALUES ('MCORE', 'Manticore',
        (SELECT id FROM manticore.task_movement_project_leads WHERE name = 'foo'));

INSERT INTO manticore.task_movement_projects (key, name, project_lead_id)
VALUES ('PROJECT', 'Test Project',
        (SELECT id FROM manticore.task_movement_project_leads WHERE name = 'person'));

INSERT INTO manticore.task_movement_projects (key, name, project_lead_id)
VALUES ('DEL', 'Project To Delete',
        (SELECT id FROM manticore.task_movement_project_leads WHERE name = 'person'));