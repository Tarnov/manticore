package com.panbet.manticore.util.bl.mt;


import com.panbet.manticore.util.IssuesStatusCounter;
import com.panbet.manticore.util.atlassian.outerlib.CommitsUtils;
import com.panbet.manticore.util.atlassian.outerlib.jira.JiraAdapter;
import com.panbet.manticore.util.atlassian.outerlib.stash.StashAdapter;
import com.panbet.stash.rest.client.api.alternative.repo.Project;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MasterTestBuilderTest {
    @Mock
    private IssuesStatusCounter issueStatusCounter;

    @Mock
    private CommitsUtils commitsUtils;

    @Mock
    private JiraAdapter jiraAdapter;

    @Mock
    private StashAdapter stashAdapter;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Repository repository;

    @Mock
    private Project project;

    @Mock
    private Executor executor;

    @Mock
    private ChangeMasterTestCheckbox changeMasterTestCheckbox;


    @Test
    void testSuccess() {
        when(stashAdapter.getRepo(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(repository);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn("projectKey");
        lenient().when(repository.getSlug()).thenReturn("repoSlug");

        final MasterTest masterTest = new MasterTestBuilder()
                .setIssueStatusCounter(issueStatusCounter)
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setChangeMasterTestCheckBox(changeMasterTestCheckbox)
                .setStashAdapter(stashAdapter)
                .setExecutor(executor)
                .setAttribute(MasterTestAttribute.PAN_AUTOTEST)
                .build();

        Assertions.assertThat(masterTest).isNotNull();
    }


    @Test
    void testNoIssueStatusCounter() {
        MasterTestBuilder builder = new MasterTestBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setExecutor(executor)
                .setChangeMasterTestCheckBox(changeMasterTestCheckbox);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNoCommitsUtils() {
        MasterTestBuilder builder = new MasterTestBuilder()
                .setIssueStatusCounter(issueStatusCounter)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setExecutor(executor)
                .setChangeMasterTestCheckBox(changeMasterTestCheckbox);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNoJiraAdapter() {
        MasterTestBuilder builder = new MasterTestBuilder()
                .setIssueStatusCounter(issueStatusCounter)
                .setCommitsUtils(commitsUtils)
                .setStashAdapter(stashAdapter)
                .setChangeMasterTestCheckBox(changeMasterTestCheckbox)
                .setExecutor(executor);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNoStashAdapter() {
        MasterTestBuilder builder = new MasterTestBuilder()
                .setIssueStatusCounter(issueStatusCounter)
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setExecutor(executor)
                .setChangeMasterTestCheckBox(changeMasterTestCheckbox);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNoExecutor() {
        MasterTestBuilder builder = new MasterTestBuilder()
                .setIssueStatusCounter(issueStatusCounter)
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setChangeMasterTestCheckBox(changeMasterTestCheckbox);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNoChangeMasterTestCheckBox() {
        MasterTestBuilder builder = new MasterTestBuilder()
                .setIssueStatusCounter(issueStatusCounter)
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setExecutor(executor);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}
