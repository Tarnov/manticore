package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentType;
import com.atlassian.confluence.api.model.content.Space;
import com.google.common.base.Preconditions;


public class ContentExpansionsBuilder extends ExpansionBuilder<String> {
    private final String prefix;

    private static final String INVALID_QUERY_MESSAGE = "Non valid expansion query";


    public ContentExpansionsBuilder() {
        this.prefix = "";
    }


    public ContentExpansionsBuilder(final String prefix) {
        Preconditions.checkArgument(checkPrefix(prefix), "Non valid prefix");
        this.prefix = prefix;
    }


    private boolean checkPrefix(final String prefix) {
        return ContentType.valueOf(prefix) != null || prefix.equals(Content.Expansions.ANCESTORS)
                || prefix.equals(Space.Expansions.HOMEPAGE);
    }


    @Override
    protected boolean checkIfValidExpansion(final String expansion) {
        return Content.Expansions.ANCESTORS.equals(expansion) || Content.Expansions.BODY.equals(expansion)
                || Content.Expansions.CHILDREN.equals(expansion) || Content.Expansions.CONTAINER.equals(expansion)
                || Content.Expansions.DESCENDANTS.equals(expansion) || Content.Expansions.HISTORY.equals(expansion)
                || Content.Expansions.SPACE.equals(expansion) || Content.Expansions.VERSION.equals(expansion);
    }


    @Override
    protected boolean checkIfValidQuery(final ExpansionQuery query) {
        return query.getQueryParts().stream()
                .map(queryPart -> queryPart.split("\\.")[0])
                .allMatch(queryPart -> queryPart.equals(Content.Expansions.SPACE)
                        || queryPart.equals(Content.Expansions.CONTAINER)
                        || queryPart.equals(Content.Expansions.HISTORY)
                        || queryPart.equals(Content.Expansions.CHILDREN)
                        || queryPart.equals(Content.Expansions.DESCENDANTS)
                        || queryPart.equals(Content.Expansions.BODY)
                        || queryPart.equals(Content.Expansions.ANCESTORS));
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
