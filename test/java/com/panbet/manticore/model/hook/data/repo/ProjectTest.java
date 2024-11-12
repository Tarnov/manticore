package com.panbet.manticore.model.hook.data.repo;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ProjectTest {
    private static final String key = "key";

    private static final Long id = 1L;

    private static final String name = "name";

    private static final String description = "description";

    private static final Boolean isPublic = false;

    private static final ProjectType type = ProjectType.NORMAL;


    @Test
    void testSuccess() {
        final ProjectBuilder projectBuilder = new ProjectBuilder();

        final Project project = projectBuilder
                .setKey(key)
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setIsPublic(isPublic)
                .setProjectType(type)
                .build();

        Assertions.assertThat(project.getKey()).isEqualTo(key);
        Assertions.assertThat(project.getId()).isEqualTo(id);
        Assertions.assertThat(project.getName()).isEqualTo(name);
        Assertions.assertThat(project.getDescription()).contains(description);
        Assertions.assertThat(project.isPublic()).isEqualTo(isPublic);
        Assertions.assertThat(project.getType()).isEqualTo(type);
    }


    @Test
    void testKeyFail() {
        ProjectBuilder builder = new ProjectBuilder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setIsPublic(isPublic)
                .setProjectType(type);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIdFail() {
        ProjectBuilder builder = new ProjectBuilder()
                .setKey(key)
                .setName(name)
                .setDescription(description)
                .setIsPublic(isPublic)
                .setProjectType(type);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNameFail() {
        ProjectBuilder builder = new ProjectBuilder()
                .setKey(key)
                .setId(id)
                .setDescription(description)
                .setIsPublic(isPublic)
                .setProjectType(type);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testDescriptionFail() {
        final ProjectBuilder projectBuilder = new ProjectBuilder();

        final Project project = projectBuilder
                .setKey(key)
                .setId(id)
                .setName(name)
                .setIsPublic(isPublic)
                .setProjectType(type)
                .build();

        Assertions.assertThat(project.getKey()).isEqualTo(key);
        Assertions.assertThat(project.getId()).isEqualTo(id);
        Assertions.assertThat(project.getName()).isEqualTo(name);
        Assertions.assertThat(project.getDescription()).isEmpty();
        Assertions.assertThat(project.isPublic()).isEqualTo(isPublic);
        Assertions.assertThat(project.getType()).isEqualTo(type);
    }


    @Test
    void testIsPublicFail() {
        ProjectBuilder builder = new ProjectBuilder()
                .setKey(key)
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setProjectType(type);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testProjectTypeFail() {
        ProjectBuilder builder = new ProjectBuilder()
                .setKey(key)
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setIsPublic(isPublic);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}