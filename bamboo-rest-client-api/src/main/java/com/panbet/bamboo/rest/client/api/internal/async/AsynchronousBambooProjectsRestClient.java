package com.panbet.bamboo.rest.client.api.internal.async;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.util.concurrent.Promise;
import com.panbet.bamboo.rest.client.api.domain.BambooProject;
import com.panbet.bamboo.rest.client.api.domain.ProjectRestClient;
import com.panbet.bamboo.rest.client.api.internal.json.ProjectJsonParser;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class AsynchronousBambooProjectsRestClient extends AbstractAsynchronousRestClient implements ProjectRestClient {
    public static final String PROJECTS_URI_PREFIX = "project";
    private final ProjectJsonParser projectJsonParser = new ProjectJsonParser();

    private final URI baseUri;

    public AsynchronousBambooProjectsRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }

    @Override
    public Promise<Iterable<BambooProject>> getAllProjects() {
        final URI uri = UriBuilder.fromUri(baseUri).path(PROJECTS_URI_PREFIX).build();
        return getAndParse(uri, projectJsonParser);
    }
}
