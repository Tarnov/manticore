package com.panbet.bamboo.rest.client.api.internal.async;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.util.concurrent.Promise;
import com.panbet.bamboo.rest.client.api.domain.ProjectPlan;
import com.panbet.bamboo.rest.client.api.domain.ProjectPlanRestClient;
import com.panbet.bamboo.rest.client.api.internal.json.ProjectPlanJsonParser;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Iterator;


public class AsynchronousBambooProjectPlanRestClient extends AbstractAsynchronousRestClient implements
        ProjectPlanRestClient {
    public static final String PLAN_URI_PREFIX = "plan";
    private final ProjectPlanJsonParser projectPlanJsonParser = new ProjectPlanJsonParser();
    private static final int MAX_RESULTS = 1000;

    private final URI baseUri;

    public AsynchronousBambooProjectPlanRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<ProjectPlan> getProjectPlan(String projectKey, String buildKey) {
        return getProjectPlan(getPlanUri(projectKey, buildKey), null);
    }


    @Override
    public Promise<ProjectPlan> getProjectPlan(String projectKey, String buildKey, Iterable<Expands> expands) {
        return getProjectPlan(getPlanUri(projectKey, buildKey), expands);
    }


    private Promise<ProjectPlan> getProjectPlan(URI uri, Iterable<Expands> expands) {
        if (expands != null) {
            uri = UriBuilder.fromUri(uri)
                    .queryParam("expand", buildString(expands))
                    .build();
        }

        return getAndParse(uri, projectPlanJsonParser);
    }


    private URI getPlanUri(String projectKey, String buildKey) {
        return UriBuilder.fromUri(baseUri)
                .path(PLAN_URI_PREFIX)
                .path(projectKey)
                .path(buildKey)
                .queryParam("max-results", MAX_RESULTS)
                .build();
    }


    private String buildString(Iterable<Expands> expands) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Expands> it = expands.iterator(); it.hasNext(); stringBuilder.append(",")) {
            Expands e = it.next();
            stringBuilder.append(e.name().toLowerCase());
        }

        return stringBuilder.toString();
    }
}
