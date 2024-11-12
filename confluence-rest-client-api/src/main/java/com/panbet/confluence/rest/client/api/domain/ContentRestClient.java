package com.panbet.confluence.rest.client.api.domain;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.util.concurrent.Promise;
import com.panbet.confluence.rest.client.api.domain.expansionBuilders.ExpansionQuery;

import java.util.Collection;


public interface ContentRestClient {
    Promise<Content> getContentById(final String contentId);


    Promise<Content> getExpandedContentById(final String contentId, ExpansionQuery query);


    Promise<Collection<Content>> getContents();


    Promise<Collection<Content>> getContents(final RequestParams params);


    Promise<Content> createNewContent(final Content content);


    Promise<Collection<Content>> getChildrensByContentId(final String contentId, final Integer limit);
}
