package com.panbet.manticore.model.hook.data.repo;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class RepositoryTest {
    private static final String slug = "slug";

    private static final Integer id = 1;

    private static final String name = "name";

    private static final String scmId = "scmId";

    private static final State state = State.AVAILABLE;

    private static final String statusMessage = "statusMessage";

    private static final Boolean isForkable = true;

    private static final Boolean isPublic = false;

    @Mock
    private Project project;


    @Test
    void testSuccess() {
        final RepositoryBuilder repositoryBuilder = new RepositoryBuilder();

        final Repository repository = repositoryBuilder
                .setSlug(slug)
                .setId(id)
                .setName(name)
                .setScmId(scmId)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic)
                .setProject(project)
                .build();

        Assertions.assertThat(repository.getSlug()).isEqualTo(slug);
        Assertions.assertThat(repository.getId()).isEqualTo(id);
        Assertions.assertThat(repository.getName()).isEqualTo(name);
        Assertions.assertThat(repository.getScmId()).isEqualTo(scmId);
        Assertions.assertThat(repository.getState()).isEqualTo(state);
        Assertions.assertThat(repository.getStatusMessage()).isEqualTo(statusMessage);
        Assertions.assertThat(repository.isForkable()).isEqualTo(isForkable);
        Assertions.assertThat(repository.isPublic()).isEqualTo(isPublic);
        Assertions.assertThat(repository.getProject()).isEqualTo(project);
    }


    @Test
    void testSlugFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setId(id)
                .setName(name)
                .setScmId(scmId)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIdFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setName(name)
                .setScmId(scmId)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNameFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setId(id)
                .setScmId(scmId)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testScmIdFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setId(id)
                .setName(name)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testStateFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setId(id)
                .setName(name)
                .setScmId(scmId)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testStatusMessageFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setId(id)
                .setName(name)
                .setScmId(scmId)
                .setState(state)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIsForkableFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setId(id)
                .setName(name)
                .setScmId(scmId)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsPublic(isPublic)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIsPublicFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setId(id)
                .setName(name)
                .setScmId(scmId)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setProject(project);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testProjectFail() {
        RepositoryBuilder builder = new RepositoryBuilder()
                .setSlug(slug)
                .setId(id)
                .setName(name)
                .setScmId(scmId)
                .setState(state)
                .setStatusMessage(statusMessage)
                .setIsForkable(isForkable)
                .setIsPublic(isPublic);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}