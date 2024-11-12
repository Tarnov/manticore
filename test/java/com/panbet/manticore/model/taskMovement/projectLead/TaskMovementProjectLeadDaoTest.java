package com.panbet.manticore.model.taskMovement.projectLead;


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
class TaskMovementProjectLeadDaoTest extends H2DatabaseConfig {

    private final TaskMovementProjectLeadDao taskMovementProjectLeadDao =
            new TaskMovementProjectLeadDaoImpl(H2DatabaseConfig.jdbcTemplate, new TaskMovementProjectLeadRowMapper());


    @BeforeAll
    static void setUpData() {
        Resource resource = new ClassPathResource("/com/panbet/manticore/db/initProjectLeadsData.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }


    @Test
    @Order(1)
    void shouldCorrectReturnAllLeads() {
        List<TaskMovementProjectLead> leads = taskMovementProjectLeadDao.getAllProjectLeads();

        Assertions.assertThat(leads).isNotEmpty();
    }


    @Test
    @Order(2)
    void shouldCorrectAddAndFindCorrectLead() {
        final String name = "backlog";
        final String displayName = "Project backlog";
        final long id = taskMovementProjectLeadDao.addProjectLead(name, displayName);

        Optional<TaskMovementProjectLead> lead = taskMovementProjectLeadDao.getProjectLeadByName(name);

        Assertions.assertThat(lead).isPresent();
        Assertions.assertThat(lead.get().getId()).isEqualTo(id);
        Assertions.assertThat(lead.get().getName()).isEqualTo(name);
        Assertions.assertThat(lead.get().getDisplayName()).isEqualTo(displayName);
    }


    @Test
    @Order(3)
    void shouldCorrectUpdateDisplayName() {
        final String name = "mcore";
        final String newDisplayName = "Manticore Service User [X]";

        taskMovementProjectLeadDao.updateProjectLeadDisplayName(name, newDisplayName);

        Optional<TaskMovementProjectLead> lead = taskMovementProjectLeadDao.getProjectLeadByName(name);

        Assertions.assertThat(lead).isPresent();
        Assertions.assertThat(lead.get().getDisplayName()).isEqualTo(newDisplayName);
    }
}
