package com.panbet.bamboo.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.util.concurrent.Promise;
import com.panbet.bamboo.rest.client.api.domain.BuildResult;
import com.panbet.bamboo.rest.client.api.domain.ResultRestClient;
import com.panbet.bamboo.rest.client.api.internal.json.BuildResultJsonParser;
import com.panbet.bamboo.rest.client.api.internal.json.BuildResultListJsonParser;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;


public class AsynchronousBambooBuildResultRestClient extends AbstractAsynchronousRestClient implements ResultRestClient {
    public static final String PLAN_URI_PREFIX = "result";

    private final BuildResultJsonParser buildResultJsonParser = new BuildResultJsonParser();

    private final BuildResultListJsonParser buildResultListJsonParser = new BuildResultListJsonParser();

    private final URI baseUri;


    public AsynchronousBambooBuildResultRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<BuildResult> getLatestBuildResult(String buildKey, String planBranch) {
        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PLAN_URI_PREFIX)
                .path(buildKey + "-" + planBranch)
                .path("latest")
                .build();

        return getAndParse(uri, buildResultJsonParser);
    }


    @Override
    public Promise<BuildResult> getBuildResult(final String buildKey, final String planBranch) {
        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PLAN_URI_PREFIX)
                .path(buildKey + "-" + planBranch)
                .build();

        return getAndParse(uri, buildResultJsonParser);
    }


    @Override
    public Promise<List<BuildResult>> getLatestBuildsResults(final String buildKey, final String planBranch,
                                                             final int limit) {
        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PLAN_URI_PREFIX)
                .path(buildKey + "-" + planBranch)
                .queryParam("max-results", limit)
                .queryParam("includeAllStates", true)
                .queryParam("expand", "results.result")
                .build();

        return getAndParse(uri, buildResultListJsonParser);
    }


    @Override
    public Promise<List<BuildResult>> getLatestSuccessBuildsResults(final String buildKey, final String planBranch,
                                                                    final int limit) {
        final URI uri = UriBuilder.fromUri(baseUri)
                .path(PLAN_URI_PREFIX)
                .path(buildKey + "-" + planBranch)
                .queryParam("max-results", limit)
                .queryParam("buildstate", "Successful")
                .queryParam("expand", "results.result")
                .build();

        return getAndParse(uri, buildResultListJsonParser);
    }
}
