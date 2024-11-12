package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.History;


public class HistoryExpansionsBuilder extends ExpansionBuilder<String> {
    private static final String HISTORY_PREFIX = "history";

    private static final String INVALID_QUERY_MESSAGE = "No nested expansions available for history, " +
            "you can only add simple expansions via addExpansions method";


    @Override
    protected boolean checkIfValidExpansion(final String expansion) {
        return History.Expansions.LATEST.equals(expansion) || History.Expansions.NEXT.equals(expansion)
                || History.Expansions.PREVIOUS.equals(expansion);
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
        return HISTORY_PREFIX;
    }
}
