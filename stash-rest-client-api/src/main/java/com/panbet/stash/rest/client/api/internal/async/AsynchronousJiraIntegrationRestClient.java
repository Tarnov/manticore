package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.jiraIntegration.JiraIntegrationCommit;
import com.panbet.stash.rest.client.api.alternative.jiraIntegration.StashJiraIntegrationJsonParser;
import com.panbet.stash.rest.client.api.domain.JiraIntegrationRestClient;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;


public class AsynchronousJiraIntegrationRestClient extends AbstractAsynchronousRestClient
        implements JiraIntegrationRestClient {
    private static final String ISSUE_KEY_URI_PREFIX = "issues";

    private static final int LIMIT = 100;

    private static final int MAX_CHANGES = 1000;

    private static final JsonObjectParser<ImmutableCollection<JiraIntegrationCommit>>
            STASH_JIRA_INTEGRATION_JSON_PARSER =
            new StashJiraIntegrationJsonParser();

    private final URI baseUri;


    AsynchronousJiraIntegrationRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<ImmutableCollection<JiraIntegrationCommit>> getJiraIntegrationCommitsByIssueKey(
            final String issueKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(issueKey), "issueKey require not blank");

        final URI uri = UriBuilder
                .fromUri(baseUri)
                .path(ISSUE_KEY_URI_PREFIX)
                .path(issueKey)
                .path("commits")
                .queryParam("limit", LIMIT)
                .queryParam("maxChanges", MAX_CHANGES)
                .build();

        return getAndParse(uri, STASH_JIRA_INTEGRATION_JSON_PARSER);
    }
}
