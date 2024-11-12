package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Preconditions;
import com.panbet.stash.rest.client.api.domain.ProjectRestClient;
import com.panbet.stash.rest.client.api.domain.StashProject;
import com.panbet.stash.rest.client.api.internal.json.StashProjectJsonParser;
import com.panbet.stash.rest.client.api.internal.json.StashProjectListJsonParser;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;


public class AsynchronousStashProjectsRestClient extends AbstractAsynchronousRestClient implements ProjectRestClient {
    private static final String PROJECTS_URI_PREFIX = "projects";

    private static final StashProjectJsonParser PROJECT_JSON_PARSER = new StashProjectJsonParser();

    private static final StashProjectListJsonParser PROJECT_LIST_JSON_PARSER = new StashProjectListJsonParser();

    private final URI baseUri;


    AsynchronousStashProjectsRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<StashProject> getProject(final String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PROJECTS_URI_PREFIX)
                .path(key)
                .build();

        return getAndParse(uri, PROJECT_JSON_PARSER);
    }


    @Override
    public Promise<StashProject> getProject(final URI projectUri) {
        return getAndParse(projectUri, PROJECT_JSON_PARSER);
    }


    @Override
    public Promise<Collection<StashProject>> getAllProjects() {
        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PROJECTS_URI_PREFIX)
                .queryParam("limit", "1000")
                .build();

        return getAndParse(uri, PROJECT_LIST_JSON_PARSER);
    }
}
