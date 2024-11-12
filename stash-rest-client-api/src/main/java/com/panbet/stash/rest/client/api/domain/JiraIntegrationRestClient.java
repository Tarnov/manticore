package com.panbet.stash.rest.client.api.domain;


import com.atlassian.util.concurrent.Promise;
import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.jiraIntegration.JiraIntegrationCommit;


public interface JiraIntegrationRestClient {
    Promise<ImmutableCollection<JiraIntegrationCommit>> getJiraIntegrationCommitsByIssueKey(final String issueKey);
}
