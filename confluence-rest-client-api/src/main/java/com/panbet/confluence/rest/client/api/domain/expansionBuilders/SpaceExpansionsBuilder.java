package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.Space;
import com.google.common.base.Preconditions;


public class SpaceExpansionsBuilder extends ExpansionBuilder<String> {
    private final String spacePrefix;

    private static final String INVALID_QUERY_MESSAGE = "Non valid expansion query";


    public SpaceExpansionsBuilder() {
        this.spacePrefix = "space";
    }


    public SpaceExpansionsBuilder(final String prefix) {
        Preconditions.checkArgument(checkPrefix(prefix), "Non valid prefix");
        this.spacePrefix = prefix;
    }


    private boolean checkPrefix(final String prefix) {
        return prefix.equals(Content.Expansions.SPACE) || prefix.equals(Content.Expansions.CONTAINER);
    }


    @Override
    protected boolean checkIfValidExpansion(final String expansion) {
        return Space.Expansions.ICON.equals(expansion) || Space.Expansions.DESCRIPTION.equals(expansion)
                || Space.Expansions.HOMEPAGE.equals(expansion);
    }


    @Override
    protected boolean checkIfValidQuery(final ExpansionQuery query) {
        return query.getQueryParts().stream()
                .allMatch(queryPart -> queryPart
                        .split("\\.")[0]
                        .equals(Space.Expansions.HOMEPAGE));
    }


    @Override
    protected String getInvalidQueryMessage() {
        return INVALID_QUERY_MESSAGE;
    }

    @Override
    protected String getPrefix() {
        return spacePrefix;
    }
}
