INSERT INTO manticore.accesses (id, access_name)
    VALUES (36, 'task-management-export-project-statistics'::varchar(100));
INSERT INTO manticore.accesses_hierarchy (parent_id, child_id) VALUES (15, 36);
INSERT INTO manticore.roles_accesses (role_id, access_id) VALUES (1, 36);