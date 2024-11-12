package com.panbet.manticore.util.atlassian.outerlib.stash.commit;


import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.atlassian.util.concurrent.Promise;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.panbet.manticore.util.atlassian.outerlib.stash.commit.*;
import com.panbet.manticore.util.atlassian.outerlib.CommitsUtils;
import com.panbet.manticore.util.atlassian.outerlib.jira.JiraAdapter;
import com.panbet.manticore.util.atlassian.outerlib.stash.StashAdapter;
import com.panbet.stash.rest.client.api.alternative.commit.Commit;
import com.panbet.stash.rest.client.api.alternative.jiraIntegration.JiraIntegrationCommit;
import com.panbet.stash.rest.client.api.alternative.repo.Project;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;
import com.panbet.stash.rest.client.api.domain.JiraStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.*;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StashCommitFactoryImplTest {
    @Mock
    private CommitsUtils commitsUtils;

    @Mock
    private JiraAdapter jiraAdapter;

    @Mock
    private StashAdapter stashAdapter;

    private static final String sinceBranch = "sinceBranch";

    private static final String untilBranch = "untilBranch";

    private static final String projectName = "projectName";

    private static final String repoName = "repoName";

    @Mock
    private Commit commit1;

    @Mock
    private Commit commit2;

    @Mock
    private JiraIntegrationCommit jiraIntegrationCommit1;

    @Mock
    private JiraIntegrationCommit jiraIntegrationCommit2;

    @Mock
    private Repository repository1;

    @Mock
    private Repository repository2;

    @Mock
    private Project project;

    private static final String projectKey = "projectKey";

    private static final String issueKey1 = "PAN-1";

    private static final String issueKey2 = "MANTICORE-2";

    private static final String commitHash1 = "hash1";

    private static final String commitHash2 = "hash2";

    @Mock
    private IssueRestClient issueRestClient;

    @Mock
    private Promise<Issue> issuePromise1;

    @Mock
    private Promise<Issue> issuePromise2;

    @Mock
    private Issue issue1;

    @Mock
    private Issue issue2;

    @Mock
    private Status issueStatus1;

    @Mock
    private Status issueStatus2;

    private final URI self = UriBuilder.fromUri("uri").build();


    private static final String statusName = "statusName";


    private final long issueStatusId1 = JiraStatus.OPEN;


    private final long issueStatusId2 = JiraStatus.CLOSED;

    @Mock
    private StashCommitFactoryUtils factoryUtils;

    private final StashCommitPanbetStatus stashCommitPanbetStatus1 = StashCommitPanbetStatus.OPEN;

    private final StashCommitPanbetStatus stashCommitPanbetStatus2 = StashCommitPanbetStatus.CLOSED;

    @Mock
    private BasicProject panbet;

    @Mock
    private BasicProject manticore;


    @Test
    void testSuccess() {
        Assertions.assertThat(new StashCommitFactoryBuilder()
                        .setCommitsUtils(commitsUtils)
                        .setJiraAdapter(jiraAdapter)
                        .setStashAdapter(stashAdapter)
                        .build())
                .isNotNull();
    }


    @Test
    void testCommitUtilsFail() {
        StashCommitFactoryBuilder builder = new StashCommitFactoryBuilder()
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testJiraAdapterFail() {
        StashCommitFactoryBuilder builder = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setStashAdapter(stashAdapter);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testStashAdapterFail() {
        StashCommitFactoryBuilder builder = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testCreateStashCommitsSuccess() {
        final Map<String, Commit> commitsMap = new HashMap<>();
        commitsMap.put(commitHash1, commit1);
        commitsMap.put(commitHash2, commit2);

        final Map<String, String> commitHashIssueKeyMap = new HashMap<>();
        commitHashIssueKeyMap.put(commitHash1, issueKey1);
        commitHashIssueKeyMap.put(commitHash2, issueKey2);

        final Map<String, Issue> commitHashIssueMap = new HashMap<>();
        commitHashIssueMap.put(commitHash1, issue1);
        commitHashIssueMap.put(commitHash2, issue2);

        final Map<String, Repository> commitHashRepoMap = new HashMap<>();
        commitHashRepoMap.put(commitHash1, repository1);
        commitHashRepoMap.put(commitHash2, repository1);

        final Map<String, StashCommitPanbetStatus> commitHashStatusMap = new HashMap<>();
        commitHashStatusMap.put(commitHash1, stashCommitPanbetStatus1);
        commitHashStatusMap.put(commitHash2, stashCommitPanbetStatus2);

        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .build();
        final Set<String> issueKeys1 = new HashSet<>();
        issueKeys1.add(issueKey1);
        final Set<String> issueKeys2 = new HashSet<>();
        issueKeys2.add(issueKey2);
        final Set<String> issueKeys = new HashSet<>();
        issueKeys.add(issueKey1);
        issueKeys.add(issueKey2);

        final Collection<BasicProject> projects = new ArrayList<>();
        projects.add(panbet);
        projects.add(manticore);

        when(commit1.getId()).thenReturn(commitHash1);
        when(commit2.getId()).thenReturn(commitHash2);
        when(stashAdapter.getCommits(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(),
                ArgumentMatchers.any())).thenReturn(commitsMap.values());
        lenient().when(stashAdapter.getRepo(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(repository1);
        lenient().when(commitsUtils.getIssueKeysFromMessages(commitsMap.values(), projectKey)).thenReturn(issueKeys);
        lenient().when(commitsUtils.getIssueKeysFromMessage(commit1, projectKey)).thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromMessage(commit2, projectKey)).thenReturn(issueKeys2);
        lenient().when(jiraAdapter.getIssuesByKeys(issueKeys)).thenReturn(issueMap);
        lenient().when(repository1.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(commit1), ArgumentMatchers.any()))
                .thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(commit2), ArgumentMatchers.any()))
                .thenReturn(issueKeys2);
        lenient().when(issue1.getKey()).thenReturn(issueKey1);
        lenient().when(issue2.getKey()).thenReturn(issueKey2);
        when(issue1.getStatus()).thenReturn(issueStatus1);
        lenient().when(issue2.getStatus()).thenReturn(issueStatus2);
        lenient().when(issueStatus1.getId()).thenReturn(issueStatusId1);
        lenient().when(issueStatus2.getId()).thenReturn(issueStatusId2);
        when(issueStatus1.getIconUrl()).thenReturn(self);
        when(issueStatus2.getIconUrl()).thenReturn(self);
        when(issueStatus1.getName()).thenReturn(statusName);
        when(issueStatus2.getName()).thenReturn(statusName);
        when(commit1.getMessage()).thenReturn(issueKey1);
        when(commit2.getMessage()).thenReturn(issueKey2);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(jiraAdapter.getAllProjects()).thenReturn(projects);
        when(panbet.getKey()).thenReturn("PAN");
        when(manticore.getKey()).thenReturn("MANTICORE");

        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        final ImmutableCollection<StashCommit> stashCommits =
                stashCommitFactory.create(sinceBranch, untilBranch, projectName, repoName);

        for (final StashCommit stashCommit : stashCommits) {
            Assertions.assertThat(stashCommit.getIssueMap()).hasSize(1);
            Assertions.assertThat(stashCommit.getIssueMap())
                    .containsEntry(commitHashIssueKeyMap.get(stashCommit.getId()), commitHashIssueMap.get(stashCommit.getId()));
            Assertions.assertThat(stashCommit.getRepository()).isEqualTo(commitHashRepoMap.get(stashCommit.getId()));
        }
    }


    @Test
    void testCreateStashCommitsSinceBranchFail() {
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(null, untilBranch, projectName, repoName));
    }


    @Test
    void testCreateStashCommitsUntilBranchFail() {
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(sinceBranch, null, projectName, repoName));
    }


    @Test
    void testCreateStashCommitsRepoNameFail() {
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(sinceBranch, untilBranch, projectName, null));
    }


    @Test
    void testCreateStashCommitsProjectNameFail() {
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(sinceBranch, untilBranch, null, repoName));
    }


    @Test
    void testCreateStashCommitsByCommitsSuccess() {
        final Map<String, Commit> commitsMap = new HashMap<>();
        commitsMap.put(commitHash1, commit1);
        commitsMap.put(commitHash2, commit2);

        final Map<String, String> commitHashIssueKeyMap = new HashMap<>();
        commitHashIssueKeyMap.put(commitHash1, issueKey1);
        commitHashIssueKeyMap.put(commitHash2, issueKey2);

        final Map<String, Issue> commitHashIssueMap = new HashMap<>();
        commitHashIssueMap.put(commitHash1, issue1);
        commitHashIssueMap.put(commitHash2, issue2);

        final Map<String, Repository> commitHashRepoMap = new HashMap<>();
        commitHashRepoMap.put(commitHash1, repository1);
        commitHashRepoMap.put(commitHash2, repository1);

        final Map<String, StashCommitPanbetStatus> commitHashStatusMap = new HashMap<>();
        commitHashStatusMap.put(commitHash1, stashCommitPanbetStatus1);
        commitHashStatusMap.put(commitHash2, stashCommitPanbetStatus2);

        final Map<String, Issue> issueMap = new HashMap<>();
        issueMap.put(issueKey1, issue1);
        issueMap.put(issueKey2, issue2);
        final Set<String> issueKeys1 = new HashSet<>();
        issueKeys1.add(issueKey1);
        final Set<String> issueKeys2 = new HashSet<>();
        issueKeys2.add(issueKey2);
        final Set<String> issueKeys = new HashSet<>();
        issueKeys.add(issueKey1);
        issueKeys.add(issueKey2);

        final Collection<BasicProject> projects = new ArrayList<>();
        projects.add(panbet);
        projects.add(manticore);

        when(commit1.getId()).thenReturn(commitHash1);
        when(commit2.getId()).thenReturn(commitHash2);
        lenient().when(stashAdapter.getRepo(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(repository1);
        lenient().when(commitsUtils.getIssueKeysFromMessages(commitsMap.values(), projectKey)).thenReturn(issueKeys);
        lenient().when(commitsUtils.getIssueKeysFromMessage(commit1, projectKey)).thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromMessage(commit2, projectKey)).thenReturn(issueKeys2);
        lenient().when(jiraAdapter.getIssuesByKeys(issueKeys)).thenReturn(issueMap);
        lenient().when(repository1.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(commit1), ArgumentMatchers.any()))
                .thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(commit2), ArgumentMatchers.any()))
                .thenReturn(issueKeys2);
        lenient().when(issue1.getKey()).thenReturn(issueKey1);
        lenient().when(issue2.getKey()).thenReturn(issueKey2);
        when(issue1.getStatus()).thenReturn(issueStatus1);
        lenient().when(issue2.getStatus()).thenReturn(issueStatus2);
        lenient().when(issueStatus1.getId()).thenReturn(issueStatusId1);
        lenient().when(issueStatus2.getId()).thenReturn(issueStatusId2);
        when(issueStatus1.getIconUrl()).thenReturn(self);
        when(issueStatus2.getIconUrl()).thenReturn(self);
        when(issueStatus1.getName()).thenReturn(statusName);
        when(issueStatus2.getName()).thenReturn(statusName);
        when(commit1.getMessage()).thenReturn(issueKey1);
        when(commit2.getMessage()).thenReturn(issueKey2);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(jiraAdapter.getAllProjects()).thenReturn(projects);
        when(panbet.getKey()).thenReturn("PAN");
        when(manticore.getKey()).thenReturn("MANTICORE");

        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        final ImmutableCollection<StashCommit> stashCommits =
                stashCommitFactory.create(projectName, repoName, commitsMap.values());

        for (final StashCommit stashCommit : stashCommits) {
            Assertions.assertThat(stashCommit.getIssueMap()).hasSize(1);
            Assertions.assertThat(stashCommit.getIssueMap())
                    .containsEntry(commitHashIssueKeyMap.get(stashCommit.getId()), commitHashIssueMap.get(stashCommit.getId()));
            Assertions.assertThat(stashCommit.getRepository()).isEqualTo(commitHashRepoMap.get(stashCommit.getId()));
        }
    }


    @Test
    void testCreateStashCommitsByCommitsRepoNameFail() {
        final Collection<Commit> commits = new ArrayList<>();
        commits.add(commit1);
        commits.add(commit2);
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(projectName, null, commits));
    }


    @Test
    void testCreateStashCommitsByCommitsProjectNameFail() {
        final Collection<Commit> commits = new ArrayList<>();
        commits.add(commit1);
        commits.add(commit2);
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(null, repoName, commits));
    }


    @Test
    void testCreateStashCommitsByCommitsFailCommits() {
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(projectName, repoName, null));
    }


    @Test
    void testCreateStashCommitsByJiraIntegrationCommitsSuccess() {
        final Map<String, Commit> commitsMap = new HashMap<>();
        commitsMap.put(commitHash1, commit1);
        commitsMap.put(commitHash2, commit2);

        final Map<String, String> commitHashIssueKeyMap = new HashMap<>();
        commitHashIssueKeyMap.put(commitHash1, issueKey1);
        commitHashIssueKeyMap.put(commitHash2, issueKey2);

        final Map<String, Issue> commitHashIssueMap = new HashMap<>();
        commitHashIssueMap.put(commitHash1, issue1);
        commitHashIssueMap.put(commitHash2, issue2);

        final Map<String, Repository> commitHashRepoMap = new HashMap<>();
        commitHashRepoMap.put(commitHash1, repository1);
        commitHashRepoMap.put(commitHash2, repository2);

        final Map<String, StashCommitPanbetStatus> commitHashStatusMap = new HashMap<>();
        commitHashStatusMap.put(commitHash1, stashCommitPanbetStatus1);
        commitHashStatusMap.put(commitHash2, stashCommitPanbetStatus2);

        final Collection<JiraIntegrationCommit> commits = new ArrayList<>();
        commits.add(jiraIntegrationCommit1);
        commits.add(jiraIntegrationCommit2);
        final Set<JiraIntegrationCommit> commits1 = new LinkedHashSet<>();
        commits1.add(jiraIntegrationCommit1);
        final Set<JiraIntegrationCommit> commits2 = new LinkedHashSet<>();
        commits2.add(jiraIntegrationCommit2);
        final Map<String, Issue> issueMap1 = new LinkedHashMap<>();
        issueMap1.put(issueKey1, issue1);
        final Map<String, Issue> issueMap2 = new LinkedHashMap<>();
        issueMap2.put(issueKey2, issue2);
        final Set<String> issueKeys1 = new LinkedHashSet<>();
        issueKeys1.add(issueKey1);
        final Set<String> issueKeys2 = new LinkedHashSet<>();
        issueKeys2.add(issueKey2);

        final Collection<BasicProject> projects = new ArrayList<>();
        projects.add(panbet);
        projects.add(manticore);

        lenient().when(commit1.getId()).thenReturn(commitHash1);
        lenient().when(commit2.getId()).thenReturn(commitHash2);
        lenient().when(commitsUtils.getIssueKeysFromMessages(commits1, projectKey)).thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromMessages(commits2, projectKey)).thenReturn(issueKeys2);
        lenient().when(commitsUtils.getIssueKeysFromMessage(jiraIntegrationCommit1, projectKey)).thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromMessage(jiraIntegrationCommit2, projectKey)).thenReturn(issueKeys2);
        lenient().when(jiraAdapter.getIssuesByKeys(issueKeys1)).thenReturn(issueMap1);
        lenient().when(jiraAdapter.getIssuesByKeys(issueKeys2)).thenReturn(issueMap2);
        lenient().when(repository1.getProject()).thenReturn(project);
        lenient().when(repository2.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(jiraIntegrationCommit1),
                ArgumentMatchers.any())).thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(jiraIntegrationCommit2),
                ArgumentMatchers.any())).thenReturn(issueKeys2);
        lenient().when(issue1.getKey()).thenReturn(issueKey1);
        lenient().when(issue2.getKey()).thenReturn(issueKey2);
        lenient().when(issue1.getStatus()).thenReturn(issueStatus1);
        lenient().when(issue2.getStatus()).thenReturn(issueStatus2);
        lenient().when(issueStatus1.getId()).thenReturn(issueStatusId1);
        lenient().when(issueStatus2.getId()).thenReturn(issueStatusId2);
        lenient().when(issueStatus1.getIconUrl()).thenReturn(self);
        lenient().when(issueStatus2.getIconUrl()).thenReturn(self);
        lenient().when(issueStatus1.getName()).thenReturn(statusName);
        lenient().when(issueStatus2.getName()).thenReturn(statusName);
        lenient().when(jiraIntegrationCommit1.getMessage()).thenReturn(issueKey1);
        lenient().when(jiraIntegrationCommit2.getMessage()).thenReturn(issueKey2);
        lenient().when(issue1.getSelf()).thenReturn(self);
        lenient().when(issue2.getSelf()).thenReturn(self);
        lenient().when(jiraIntegrationCommit1.getRepository()).thenReturn(repository1);
        lenient().when(jiraIntegrationCommit2.getRepository()).thenReturn(repository2);
        lenient().when(jiraAdapter.getAllProjects()).thenReturn(projects);
        lenient().when(panbet.getKey()).thenReturn("PAN");
        lenient().when(manticore.getKey()).thenReturn("MANTICORE");

        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        final ImmutableCollection<StashCommit> stashCommits =
                stashCommitFactory.create(commits);

        for (final StashCommit stashCommit : stashCommits) {
            Assertions.assertThat(stashCommit.getIssueMap()).hasSize(1);
            Assertions.assertThat(stashCommit.getIssueMap())
                    .containsEntry(commitHashIssueKeyMap.get(stashCommit.getId()), commitHashIssueMap.get(stashCommit.getId()));
            Assertions.assertThat(stashCommit.getRepository()).isEqualTo(commitHashRepoMap.get(stashCommit.getId()));
        }
    }


    @Test
    void testCreateStashCommitsByJiraIntegrationCommitsFail() {
        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> stashCommitFactory.create(null));
    }


    @Test
    void testCreateStashCommitsByCommitsAndRepositorySuccess() {
        final Map<String, Commit> commitsMap = new HashMap<>();
        commitsMap.put(commitHash1, commit1);
        commitsMap.put(commitHash2, commit2);

        final Map<String, String> commitHashIssueKeyMap = new HashMap<>();
        commitHashIssueKeyMap.put(commitHash1, issueKey1);
        commitHashIssueKeyMap.put(commitHash2, issueKey2);

        final Map<String, Issue> commitHashIssueMap = new HashMap<>();
        commitHashIssueMap.put(commitHash1, issue1);
        commitHashIssueMap.put(commitHash2, issue2);

        final Map<String, Repository> commitHashRepoMap = new HashMap<>();
        commitHashRepoMap.put(commitHash1, repository1);
        commitHashRepoMap.put(commitHash2, repository1);

        final Map<String, StashCommitPanbetStatus> commitHashStatusMap = new HashMap<>();
        commitHashStatusMap.put(commitHash1, stashCommitPanbetStatus1);
        commitHashStatusMap.put(commitHash2, stashCommitPanbetStatus2);

        final Map<String, Issue> issueMap = new HashMap<>();
        issueMap.put(issueKey1, issue1);
        issueMap.put(issueKey2, issue2);
        final Set<String> issueKeys1 = new HashSet<>();
        issueKeys1.add(issueKey1);
        final Set<String> issueKeys2 = new HashSet<>();
        issueKeys2.add(issueKey2);
        final Set<String> issueKeys = new HashSet<>();
        issueKeys.add(issueKey1);
        issueKeys.add(issueKey2);

        final Collection<BasicProject> projects = new ArrayList<>();
        projects.add(panbet);
        projects.add(manticore);

        when(commit1.getId()).thenReturn(commitHash1);
        lenient().when(commit2.getId()).thenReturn(commitHash2);
        lenient().when(stashAdapter.getRepo(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(repository1);
        lenient().when(commitsUtils.getIssueKeysFromMessages(commitsMap.values(), projectKey)).thenReturn(issueKeys);
        lenient().when(commitsUtils.getIssueKeysFromMessage(commit1, projectKey)).thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromMessage(commit2, projectKey)).thenReturn(issueKeys2);
        lenient().when(jiraAdapter.getIssuesByKeys(issueKeys)).thenReturn(issueMap);
        lenient().when(repository1.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(commit1), ArgumentMatchers.any()))
                .thenReturn(issueKeys1);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(ArgumentMatchers.eq(commit2), ArgumentMatchers.any()))
                .thenReturn(issueKeys2);
        lenient().when(issue1.getKey()).thenReturn(issueKey1);
        lenient().when(issue2.getKey()).thenReturn(issueKey2);
        when(issue1.getStatus()).thenReturn(issueStatus1);
        lenient().when(issue2.getStatus()).thenReturn(issueStatus2);
        lenient().when(issueStatus1.getId()).thenReturn(issueStatusId1);
        lenient().when(issueStatus2.getId()).thenReturn(issueStatusId2);
        when(issueStatus1.getIconUrl()).thenReturn(self);
        when(issueStatus2.getIconUrl()).thenReturn(self);
        when(issueStatus1.getName()).thenReturn(statusName);
        when(issueStatus2.getName()).thenReturn(statusName);
        when(commit1.getMessage()).thenReturn(issueKey1);
        when(commit2.getMessage()).thenReturn(issueKey2);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(jiraAdapter.getAllProjects()).thenReturn(projects);
        when(panbet.getKey()).thenReturn("PAN");
        when(manticore.getKey()).thenReturn("MANTICORE");

        final StashCommitFactory stashCommitFactory = new StashCommitFactoryBuilder()
                .setCommitsUtils(commitsUtils)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .build();

        final ImmutableCollection<StashCommit> stashCommits =
                stashCommitFactory.create(repository1, commitsMap.values());

        for (final StashCommit stashCommit : stashCommits) {
            Assertions.assertThat(stashCommit.getIssueMap()).hasSize(1);
            Assertions.assertThat(stashCommit.getIssueMap())
                    .containsEntry(commitHashIssueKeyMap.get(stashCommit.getId()), commitHashIssueMap.get(stashCommit.getId()));
            Assertions.assertThat(stashCommit.getRepository()).isEqualTo(commitHashRepoMap.get(stashCommit.getId()));
        }
    }
}