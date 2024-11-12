package com.panbet.manticore.model.taskMovement.project;


import com.panbet.manticore.config.H2DatabaseConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@Profile("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskMovementProjectDaoTest extends H2DatabaseConfig {

    private final TaskMovementProjectDao taskMovementProjectDao =
            new TaskMovementProjectDaoImpl(H2DatabaseConfig.jdbcTemplate, new TaskMovementProjectExtractor());

    private final String KEY = "MCORE";


    @BeforeAll
    static void setUpData() {
        Resource resource = new ClassPathResource("/com/panbet/manticore/db/initProjectsData.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }


    @Test
    @Order(1)
    void shouldCorrectReturnAllProjects() {
        List<TaskMovementProject> projects = taskMovementProjectDao.getAllProjects();

        Assertions.assertThat(projects).isNotEmpty();
    }


    @Test
    @Order(2)
    void shouldCorrectReturnProjectByKey() {
        Optional<TaskMovementProject> project = taskMovementProjectDao.getProjectByKey(KEY);

        Assertions.assertThat(project).isPresent();
        Assertions.assertThat(project.get().getKey()).isEqualTo(KEY);
    }


    @Test
    @Order(3)
    void shouldCorrectDeleteProject() {
        taskMovementProjectDao.deleteProject("DEL");
        Optional<TaskMovementProject> project = taskMovementProjectDao.getProjectByKey("DEL");

        Assertions.assertThat(project).isEmpty();
    }
}
