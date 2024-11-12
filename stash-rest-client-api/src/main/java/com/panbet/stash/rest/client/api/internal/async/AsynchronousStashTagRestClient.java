package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.stash.repository.Tag;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Preconditions;
import com.panbet.stash.rest.client.api.StashRestClient;
import com.panbet.stash.rest.client.api.domain.TagRestClient;
import com.panbet.stash.rest.client.api.internal.json.StashTagListJsonParser;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;


public class AsynchronousStashTagRestClient extends AbstractAsynchronousRestClient implements TagRestClient {
    private static final StashTagListJsonParser STASH_TAG_LIST_JSON_PARSER = new StashTagListJsonParser();

    private static final String TAGS_URI_PREFIX = "tags";

    private static final String FILTER_TEXT_VAR_NAME = "filterText";

    private static final String ORDER_BY_ENUM = "orderBy";

    private static final String LIMIT_VAR_NAME = "limit";

    private static final int DEFAULT_LIMIT = 1000;

    private final URI baseUri;


    AsynchronousStashTagRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<Collection<Tag>> getTags(final String project, final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(TAGS_URI_PREFIX)
                .queryParam(LIMIT_VAR_NAME, DEFAULT_LIMIT)
                .build();

        return getTags(uri);
    }


    @Override
    public Promise<Collection<Tag>> getTags(final String filterText, final String project, final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(filterText), "filterText require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(TAGS_URI_PREFIX)
                .queryParam(FILTER_TEXT_VAR_NAME, filterText)
                .queryParam(LIMIT_VAR_NAME, DEFAULT_LIMIT)
                .build();

        return getTags(uri);
    }


    @Override
    public Promise<Collection<Tag>> getTags(final OrderBy orderBy, final String project, final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(orderBy != null, "orderBy require not null");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(TAGS_URI_PREFIX)
                .queryParam(ORDER_BY_ENUM, orderBy)
                .queryParam(LIMIT_VAR_NAME, DEFAULT_LIMIT)
                .build();

        return getTags(uri);
    }


    @Override
    public Promise<Collection<Tag>> getTags(final String filterText, final OrderBy orderBy, final String project,
                                            final String repo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(repo), "repo require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(project), "project require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(filterText), "filterText require not blank");
        Preconditions.checkArgument(orderBy != null, "orderBy require not null");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(StashRestClient.PROJECTS_URI_PREFIX)
                .path(project)
                .path(StashRestClient.REPO_URI_PREFIX)
                .path(repo)
                .path(TAGS_URI_PREFIX)
                .queryParam(FILTER_TEXT_VAR_NAME, filterText)
                .queryParam(ORDER_BY_ENUM, orderBy)
                .queryParam(LIMIT_VAR_NAME, DEFAULT_LIMIT)
                .build();

        return getTags(uri);
    }


    private Promise<Collection<Tag>> getTags(final URI uri) {
        return getAndParse(uri, STASH_TAG_LIST_JSON_PARSER);
    }
}
