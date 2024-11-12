package com.panbet.bamboo.rest.client.api.internal.async;

import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.panbet.bamboo.rest.client.api.BambooRestClient;
import com.panbet.bamboo.rest.client.api.domain.ProjectPlanRestClient;
import com.panbet.bamboo.rest.client.api.domain.ProjectRestClient;
import com.panbet.bamboo.rest.client.api.domain.ResultRestClient;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class AsynchronousBambooRestClient implements BambooRestClient {
    private ProjectRestClient projectRestClient;
    private ProjectPlanRestClient projectPlanRestClient;
    private ResultRestClient resultRestClient;
    private final DisposableHttpClient httpClient;


    public AsynchronousBambooRestClient(final URI serverUri, final DisposableHttpClient httpClient) {
        final URI baseUri = UriBuilder.fromUri(serverUri).path("/rest/api/latest").build();

        projectRestClient = new AsynchronousBambooProjectsRestClient(baseUri, httpClient);
        projectPlanRestClient = new AsynchronousBambooProjectPlanRestClient(baseUri, httpClient);
        resultRestClient = new AsynchronousBambooBuildResultRestClient(baseUri, httpClient);

        this.httpClient = httpClient;

    }


    @Override
    public ProjectRestClient getProjectClient() {
        return projectRestClient;
    }


    @Override
    public ProjectPlanRestClient getProjectPlanRestClient() {
        return projectPlanRestClient;
    }


    @Override
    public ResultRestClient getResultRestClient() {
        return resultRestClient;
    }


    @Override
    public void close() throws IOException {
        try {
            httpClient.destroy();
        } catch (Exception e) {
            throw (e instanceof IOException) ? ((IOException) e) : new IOException(e);
        }
    }
}
