package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.Diff;
import com.atlassian.stash.content.Path;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Preconditions;
import com.panbet.stash.rest.client.api.StashRestClient;
import com.panbet.stash.rest.client.api.alternative.commit.Commit;
import com.panbet.stash.rest.client.api.alternative.commit.StashCommitJsonParser;
import com.panbet.stash.rest.client.api.alternative.commit.StashManyCommitsJsonParser;
import com.panbet.stash.rest.client.api.domain.CommitsRestClient;
import com.panbet.stash.rest.client.api.internal.json.StashChangesJsonParser;
import com.panbet.stash.rest.client.api.internal.json.StashManyDiffsJsonParser;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;


public class AsynchronousStashCommitRestClient extends AbstractAsynchronousRestClient implements CommitsRestClient {
    private static final String LIMIT_COMMITS_INFO = "1000";

    private static final String COMMITS_URI_PREFIX = "commits";

    private static final String CHANGES_URI_PREFIX = "changes";

    private static final String DIFF_URI_PREFIX = "diff";

    private static final String WHITESPACE_GET_PREFIX = "whitespace";

    private static final String SINCE_PREFIX = "since";

    private static final String UNTIL_PREFIX = "until";

    private static final String IGNORE_MISSING_PARAM = "ignoreMissing";

    private static final String LIMIT_PREFIX = "limit";

    private static final JsonObjectParser<Commit> STASH_COMMIT_JSON_PARSER = new StashCommitJsonParser();

    private static final JsonObjectParser<Collection<Commit>> STASH_MANY_COMMITS_JSON_PARSER =
            new StashManyCommitsJsonParser();

    private static final StashChangesJsonParser STASH_CHANGES_JSON_PARSER = new StashChangesJsonParser();

    private static final StashManyDiffsJsonParser STASH_MANY_DIFFS_JSON_PARSER = new StashManyDiffsJsonParser();

    private final URI baseUri;


    AsynchronousStashCommitRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<Commit> getCommit(final String id, final String project, final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "id require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(COMMITS_URI_PREFIX)
                .path(id)
                .build();

        return getAndParse(uri, STASH_COMMIT_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Commit>> getCommits(final String sinceBrunch, final String untilBranch,
                                                  final String project, final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(sinceBrunch), "sinceBrunch require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(untilBranch), "untilBranch require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(COMMITS_URI_PREFIX)
                .queryParam(UNTIL_PREFIX, untilBranch)
                .queryParam(SINCE_PREFIX, sinceBrunch)
                .queryParam(LIMIT_PREFIX, LIMIT_COMMITS_INFO)
                .queryParam(IGNORE_MISSING_PARAM, true)
                .build();

        return getAndParse(uri, STASH_MANY_COMMITS_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Commit>> getCommits(final String until, final String project, final String repo,
                                                  final int limit) {
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(until), "until require not blank");
        Preconditions.checkArgument(limit >= 1 || limit <= 1000, "until must be between 1 to 1000");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(COMMITS_URI_PREFIX)
                .queryParam(UNTIL_PREFIX, until)
                .queryParam(LIMIT_PREFIX, limit)
                .build();

        return getAndParse(uri, STASH_MANY_COMMITS_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Change>> getChanges(final String hash, final String project, final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(hash), "hash require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(COMMITS_URI_PREFIX)
                .path(hash)
                .path(CHANGES_URI_PREFIX)
                .queryParam(LIMIT_PREFIX, LIMIT_COMMITS_INFO)
                .build();

        return getAndParse(uri, STASH_CHANGES_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Diff>> getDiffsByPath(final String hash, final Path path, final String project,
                                                    final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(hash), "hash require not blank");
        Preconditions.checkArgument(path != null, "path require not null");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(COMMITS_URI_PREFIX)
                .path(hash)
                .path(DIFF_URI_PREFIX)
                .path(path.toString())
                .queryParam(WHITESPACE_GET_PREFIX, "ignore-all")
                .build();

        return getAndParse(uri, STASH_MANY_DIFFS_JSON_PARSER);
    }
}
