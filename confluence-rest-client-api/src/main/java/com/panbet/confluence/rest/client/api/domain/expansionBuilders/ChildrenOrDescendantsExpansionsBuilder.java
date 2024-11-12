package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentType;
import com.google.common.base.Preconditions;


public class ChildrenOrDescendantsExpansionsBuilder extends ExpansionBuilder<ContentType> {
    private final String prefix;

    private static final String INVALID_QUERY_MESSAGE = "Non valid expansion query";


    public ChildrenOrDescendantsExpansionsBuilder(final String prefix) {
        Preconditions.checkArgument(checkPrefix(prefix), "Non valid prefix");
        this.prefix = prefix;
    }


    private boolean checkPrefix(final String prefix) {
        return prefix.equals(Content.Expansions.DESCENDANTS) || prefix.equals(Content.Expansions.CHILDREN);
    }


    @Override
    protected boolean checkIfValidExpansion(final ContentType expansion) {
        return true;
    }


    @Override
    protected boolean checkIfValidQuery(final ExpansionQuery query) {
        return query.getQueryParts().stream()
                .allMatch(queryPart -> ContentType.valueOf(queryPart.split("\\.")[0]) != null);
    }


    @Override
    protected String getInvalidQueryMessage() {
        return INVALID_QUERY_MESSAGE;
    }


    @Override
    protected String getPrefix() {
        return prefix;
    }
}
