package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.panbet.stash.rest.client.api.StashRestClient;
import com.panbet.stash.rest.client.api.domain.*;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;


public class AsynchronousStashRestClient implements StashRestClient {
    private final DisposableHttpClient defaultHttpClient;

    private final ProjectRestClient projectRestClient;

    private final ReposRestClient reposRestClient;

    private final CommitsRestClient commitsRestClient;

    private final BranchRestClient branchRestClient;

    private final JiraIntegrationRestClient jiraIntegrationRestClient;

    private final TagRestClient tagRestClient;


    AsynchronousStashRestClient(final URI serverUri, final DisposableHttpClient defaultHttpClient) {
        this.defaultHttpClient = defaultHttpClient;

        final URI baseUri = UriBuilder.fromUri(serverUri)
                .path("/rest/api/latest")
                .build();

        final URI jiraIntegrationUri = UriBuilder.fromUri(serverUri)
                .path("/rest/jira/latest")
                .build();

        final URI branchUri = UriBuilder.fromUri(serverUri)
                .path("/rest/branch-utils/latest")
                .build();

        final DisposableHttpClient disposableHttpClient =
                new AsynchronousHttpClientFactory().createClient(defaultHttpClient);

        projectRestClient = new AsynchronousStashProjectsRestClient(baseUri, disposableHttpClient);
        reposRestClient = new AsynchronousStashReposRestClient(baseUri, disposableHttpClient);
        commitsRestClient = new AsynchronousStashCommitRestClient(baseUri, disposableHttpClient);
        branchRestClient = new AsynchronousStashBranchRestClient(branchUri, baseUri, disposableHttpClient);
        jiraIntegrationRestClient = new AsynchronousJiraIntegrationRestClient(jiraIntegrationUri, disposableHttpClient);
        tagRestClient = new AsynchronousStashTagRestClient(baseUri, disposableHttpClient);
    }


    @Override
    public ProjectRestClient getProjectClient() {
        return projectRestClient;
    }


    @Override
    public ReposRestClient getReposClient() {
        return reposRestClient;
    }


    @Override
    public CommitsRestClient getCommitRestClient() {
        return commitsRestClient;
    }


    @Override
    public JiraIntegrationRestClient getJiraIntegrationRestClient() {
        return jiraIntegrationRestClient;
    }


    @Override
    public TagRestClient getTagRestClient() {
        return tagRestClient;
    }


    @Override
    public BranchRestClient getBranchRestClient() {
        return branchRestClient;
    }


    @Override
    public void close() throws IOException {
        try {
            defaultHttpClient.destroy();
        } catch (final Exception e) {
            throw (e instanceof IOException) ? ((IOException) e) : new IOException(e);
        }
    }
}
