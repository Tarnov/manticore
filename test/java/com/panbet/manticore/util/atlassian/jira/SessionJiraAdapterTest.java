package com.panbet.manticore.util.atlassian.jira;


import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.util.concurrent.Promise;
import com.panbet.manticore.util.atlassian.outerlib.jira.JiraAdapter;
import com.panbet.manticore.util.atlassian.outerlib.jira.SessionJiraAdapter;
import com.panbet.manticore.util.bl.auth.logout.LocalSessionRegistry;
import com.panbet.manticore.util.atlassian.Atlassian;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SessionJiraAdapterTest {

    @Mock
    private HttpSession httpSession;

    @Mock
    private Atlassian atlassian;

    @Mock
    private IssueRestClient issueRestClient;

    @Mock
    private SearchRestClient searchRestClient;

    @Mock
    private UserRestClient userRestClient;

    @Mock
    private JiraRestClient jiraRestClient;

    @Mock
    private Issue issue;

    @Mock
    private SearchResult searchResult;

    @Mock
    private Iterable<Transition> transitions;

    @Mock
    private User user;

    @Mock
    private Promise<Issue> promiseIssue;

    @Mock
    private Promise<SearchResult> promiseSearchResult;

    @Mock
    private Promise<Iterable<Transition>> promiseTransitions;

    @Mock
    private Promise<User> promiseUser;

    @Mock
    private LocalSessionRegistry localSessionRegistry;

    private JiraAdapter jiraAdapter;


    @BeforeEach
    void setUp() {
        jiraAdapter = new SessionJiraAdapter(httpSession, atlassian, localSessionRegistry);
    }


    @Test
    void testGetIssuesByCorrectKeys() {
        final String key = "key";

        final Set<String> keys = new HashSet<>();
        keys.add(key);

        when(atlassian.createJiraRestClient(httpSession)).thenReturn(jiraRestClient);
        when(jiraRestClient.getIssueClient()).thenReturn(issueRestClient);
        when(issueRestClient.getIssue(key)).thenReturn(promiseIssue);
        when(promiseIssue.fail(ArgumentMatchers.any())).thenReturn(promiseIssue);
        when(promiseIssue.claim()).thenReturn(issue);

        Assertions.assertThat(jiraAdapter.getIssuesByKeys(keys)).containsEntry(key,issue);
        Assertions.assertThat(jiraAdapter.getIssuesByKeys(keys)).hasSize(1);
    }


    @Test
    void testGetIssuesException() {
        final String key = "key";

        final Set<String> keys = new HashSet<>();
        keys.add(key);
        lenient().when(atlassian.createJiraRestClient(httpSession)).thenReturn(jiraRestClient);
        lenient().when(jiraRestClient.getIssueClient()).thenReturn(issueRestClient);
        lenient().when(issueRestClient.getIssue(key)).thenReturn(promiseIssue);
        lenient().when(promiseIssue.fail(ArgumentMatchers.any())).thenReturn(promiseIssue);

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> jiraAdapter.getIssuesByKeys(keys));
    }


    @Test
    void testHttpSessionNull() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new SessionJiraAdapter(null, atlassian, localSessionRegistry));
    }


    @Test
    void testAtlassianNull() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new SessionJiraAdapter(httpSession, null, localSessionRegistry));
    }


    @Test
    void testLocalSessionRegistryNull() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new SessionJiraAdapter(httpSession, atlassian, null));
    }


    @Test
    void testCreationWithSuccess() {
        Assertions.assertThat(new SessionJiraAdapter(httpSession, atlassian, localSessionRegistry)).isNotNull();
    }
}