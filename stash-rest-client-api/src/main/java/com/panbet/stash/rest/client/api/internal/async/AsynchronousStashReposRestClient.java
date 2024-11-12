package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Preconditions;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;
import com.panbet.stash.rest.client.api.alternative.repo.StashRepoJsonParser;
import com.panbet.stash.rest.client.api.alternative.repo.StashRepoListJsonParser;
import com.panbet.stash.rest.client.api.domain.ReposRestClient;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;


public class AsynchronousStashReposRestClient extends AbstractAsynchronousRestClient implements ReposRestClient {
    private static final String REPO_URI_PREFIX = "repos";

    private static final String PROJECTS_URI_PREFIX = "projects";

    private static final JsonObjectParser<Repository> STASH_REPO_JSON_PARSER = new StashRepoJsonParser();

    private static final JsonObjectParser<Collection<Repository>> STASH_REPO_LIST_JSON_PARSER =
            new StashRepoListJsonParser();

    private final URI baseUri;


    AsynchronousStashReposRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<Repository> getRepository(final String projectName, final String repoName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(projectName), "projectName require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repoName), "repoName require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PROJECTS_URI_PREFIX)
                .path(projectName)
                .path(REPO_URI_PREFIX)
                .path(repoName)
                .build();

        return getAndParse(uri, STASH_REPO_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Repository>> getRepositories(final String projectName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(projectName), "projectName require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PROJECTS_URI_PREFIX)
                .path(projectName)
                .path(REPO_URI_PREFIX)
                .queryParam("limit", "1000")
                .build();

        return getAndParse(uri, STASH_REPO_LIST_JSON_PARSER);
    }
}
