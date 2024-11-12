package com.panbet.manticore.util.atlassian.outerlib.stash.commit;


import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.google.common.collect.ImmutableMap;
import com.panbet.manticore.util.atlassian.outerlib.stash.commit.StashCommit;
import com.panbet.manticore.util.atlassian.outerlib.stash.commit.StashCommitPanbetStatus;
import com.panbet.manticore.util.atlassian.stash.constants.StashProjects;
import com.panbet.manticore.util.atlassian.outerlib.CommitsUtils;
import com.panbet.stash.rest.client.api.alternative.repo.Project;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;
import com.panbet.stash.rest.client.api.domain.JiraStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StashCommitPanbetStatusTest {
    @Mock
    private CommitsUtils commitsUtils;

    @Mock
    private Repository repository;

    @Mock
    private Project project;

    @Mock
    private StashCommit commit;

    @Mock
    private Issue issue1;

    @Mock
    private Issue issue2;

    @Mock
    private Issue issue3;

    @Mock
    private Issue issue4;

    @Mock
    private Issue issue5;

    @Mock
    private Issue issue6;

    private static final String issueKey1 = "PAN-11111";

    private static final String issueKey2 = "PAN-22222";

    private static final String issueKey3 = "PAN-33333";

    private static final String issueKey4 = "MANTICORE-11111";

    private static final String issueKey5 = "MANTICORE-22222";

    private static final String issueKey6 = "MANTICORE-33333";

    @Mock
    private Status status1;

    @Mock
    private Status status2;

    @Mock
    private Status status3;


    @Test
    void testComputeStatusSuccessOPEN() {
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .build();

        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getId()).thenReturn(JiraStatus.OPEN);
        when(issue2.getStatus()).thenReturn(status2);
        when(status2.getId()).thenReturn(JiraStatus.TESTED);
        when(issue3.getStatus()).thenReturn(status3);
        when(status3.getId()).thenReturn(JiraStatus.CLOSED);
        when(commit.getIssueMap()).thenReturn(issueMap);

        Assertions.assertThat(StashCommitPanbetStatus.computePanbetCommitStatus(commit, StashProjects.PAN.getKey()))
                .isEqualTo(StashCommitPanbetStatus.OPEN);
    }


    @Test
    void testComputeStatusSuccessRESOLVED() {
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .build();

        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getId()).thenReturn(JiraStatus.RESOLVED);
        when(issue2.getStatus()).thenReturn(status2);
        when(status2.getId()).thenReturn(JiraStatus.TESTED);
        when(issue3.getStatus()).thenReturn(status3);
        when(status3.getId()).thenReturn(JiraStatus.CLOSED);
        when(commit.getIssueMap()).thenReturn(issueMap);

        Assertions.assertThat(StashCommitPanbetStatus.computePanbetCommitStatus(commit, StashProjects.PAN.getKey()))
                .isEqualTo(StashCommitPanbetStatus.RESOLVED);
    }


    @Test
    void testComputeStatusSuccessTESTED() {
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .build();

        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getId()).thenReturn(JiraStatus.TESTED);
        when(issue2.getStatus()).thenReturn(status2);
        when(status2.getId()).thenReturn(JiraStatus.TESTED);
        when(issue3.getStatus()).thenReturn(status3);
        when(status3.getId()).thenReturn(JiraStatus.CLOSED);
        when(commit.getIssueMap()).thenReturn(issueMap);
        
        Assertions.assertThat(StashCommitPanbetStatus.computePanbetCommitStatus(commit, StashProjects.PAN.getKey()))
                .isEqualTo(StashCommitPanbetStatus.TESTED);
    }


    @Test
    void testComputeStatusFailCheckIssueKeys() {
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey4, issue4)
                .build();

        when(commit.getIssueMap()).thenReturn(issueMap);

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> StashCommitPanbetStatus.computePanbetCommitStatus(commit, StashProjects.PAN.getKey()));
    }


    @Test
    void testComputeStatusFailCheckIssuesStatus() {
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .build();

        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getId()).thenReturn(JiraStatus.BACKLOG);
        when(commit.getIssueMap()).thenReturn(issueMap);

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> StashCommitPanbetStatus.computePanbetCommitStatus(commit, StashProjects.PAN.getKey()));
    }
}