package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Preconditions;
import com.panbet.stash.rest.client.api.StashRestClient;
import com.panbet.stash.rest.client.api.domain.BranchInfo;
import com.panbet.stash.rest.client.api.domain.BranchRestClient;
import com.panbet.stash.rest.client.api.internal.json.StashBranchesJsonParser;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;


public class AsynchronousStashBranchRestClient extends AbstractAsynchronousRestClient implements BranchRestClient {
    private static final String BRANCH_URI_PREFIX = "branches";

    private static final String INFO_URI_PREFIX = "info";

    private static final StashBranchesJsonParser STASH_BRANCHES_JSON_PARSER = new StashBranchesJsonParser();

    private final URI baseUri;

    private final URI baseBranchUri;


    AsynchronousStashBranchRestClient(final URI baseBranchUri, final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
        this.baseBranchUri = baseBranchUri;
    }


    @Override
    public Promise<Collection<BranchInfo>> getBranchesByCommitId(final String commitId, final String project,
                                                                 final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(commitId), "commitId require not blank");

        final URI uri = UriBuilder.fromUri(baseBranchUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(BRANCH_URI_PREFIX)
                .path(INFO_URI_PREFIX)
                .path(commitId)
                .build();

        return getAndParse(uri, STASH_BRANCHES_JSON_PARSER);
    }


    public Promise<Collection<BranchInfo>> getBranches(final String project, final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(BRANCH_URI_PREFIX)
                .queryParam("limit", "1000")
                .build();

        return getAndParse(uri, STASH_BRANCHES_JSON_PARSER);
    }
}
