package com.panbet.confluence.rest.client.api.internal.async;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Preconditions;
import com.panbet.confluence.rest.client.api.domain.AvailableRequestParams;
import com.panbet.confluence.rest.client.api.domain.ContentRestClient;
import com.panbet.confluence.rest.client.api.domain.RequestParams;
import com.panbet.confluence.rest.client.api.domain.expansionBuilders.ExpansionQuery;
import com.panbet.confluence.rest.client.api.internal.json.generator.ConfluenceContentJsonGenerator;
import com.panbet.confluence.rest.client.api.internal.json.parser.ConfluenceContentJsonParser;
import com.panbet.confluence.rest.client.api.internal.json.parser.ConfluenceContentListJsonParser;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;


public class AsynchronousConfluenceContentRestClient extends AbstractAsynchronousRestClient implements
        ContentRestClient {
    private static final String CONTENT_PREFIX = "content";

    private static final String EXPAND_PARAM_NAME = "expand";

    private static final ConfluenceContentJsonParser CONFLUENCE_CONTENT_JSON_PARSER = new ConfluenceContentJsonParser();

    private static final ConfluenceContentListJsonParser CONFLUENCE_CONTENT_LIST_JSON_PARSER = new ConfluenceContentListJsonParser();

    private static final ConfluenceContentJsonGenerator CONFLUENCE_CONTENT_JSON_GENERATOR = new ConfluenceContentJsonGenerator();

    private final URI baseUri;


    AsynchronousConfluenceContentRestClient(final URI baseUri, final HttpClient client) {
        super(client);
        this.baseUri = baseUri;
    }


    @Override
    public Promise<Content> getContentById(final String contentId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(contentId), "contentId require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(CONTENT_PREFIX)
                .path(contentId)
                .build();

        return getAndParse(uri, CONFLUENCE_CONTENT_JSON_PARSER);
    }


    @Override
    public Promise<Content> getExpandedContentById(final String contentId, final ExpansionQuery query) {
        Preconditions.checkArgument(StringUtils.isNotBlank(contentId), "contentId require not blank");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(CONTENT_PREFIX)
                .path(contentId)
                .queryParam(EXPAND_PARAM_NAME, query.getQuery())
                .build();

        return getAndParse(uri, CONFLUENCE_CONTENT_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Content>> getContents() {
        final URI uri = UriBuilder.fromUri(baseUri)
                .path(CONTENT_PREFIX)
                .build();

        return getAndParse(uri, CONFLUENCE_CONTENT_LIST_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Content>> getContents(final RequestParams params) {
        final UriBuilder builder = UriBuilder.fromUri(baseUri)
                .path(CONTENT_PREFIX);

        for (AvailableRequestParams key : params.getParamsKeys()) {
            builder.queryParam(key.getName(), params.getParamValue(key));
        }

        URI uri = builder.build();

        return getAndParse(uri, CONFLUENCE_CONTENT_LIST_JSON_PARSER);
    }


    @Override
    public Promise<Content> createNewContent(final Content content) {
        Preconditions.checkArgument(content != null, "content require not null");
        final URI uri = UriBuilder.fromUri(baseUri)
                .path(CONTENT_PREFIX)
                .build();

        return postAndParse(uri, content, CONFLUENCE_CONTENT_JSON_GENERATOR, CONFLUENCE_CONTENT_JSON_PARSER);
    }


    @Override
    public Promise<Collection<Content>> getChildrensByContentId(final String contentId, final Integer limit) {
        Preconditions.checkArgument(contentId != null, "contentId require not null");
        Preconditions.checkArgument(limit != null, "limit require not null");

        final URI uri = UriBuilder.fromUri(baseUri)
                .path(CONTENT_PREFIX)
                .path(contentId)
                .path("child")
                .path("page")
                .queryParam("limit", limit)
                .build();

        return getAndParse(uri, CONFLUENCE_CONTENT_LIST_JSON_PARSER);
    }
}
