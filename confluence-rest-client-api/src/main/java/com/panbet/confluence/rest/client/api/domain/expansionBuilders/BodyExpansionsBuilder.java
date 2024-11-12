package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.ContentRepresentation;


public class BodyExpansionsBuilder extends ExpansionBuilder<ContentRepresentation> {
    private static final String BODY_PREFIX = "body";

    private static final String INVALID_QUERY_MESSAGE = "No nested expansions available for body, " +
            "you can only add simple expansions via addExpansions method";


    @Override
    protected boolean checkIfValidExpansion(final ContentRepresentation expansion) {
        return true;
    }


    @Override
    protected boolean checkIfValidQuery(final ExpansionQuery query) {
        return false;
    }


    @Override
    protected String getInvalidQueryMessage() {
        return INVALID_QUERY_MESSAGE;
    }


    @Override
    protected String getPrefix() {
        return BODY_PREFIX;
    }
}
