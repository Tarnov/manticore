package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.google.common.base.Joiner;

import java.util.Collection;

public class ExpansionQuery {
    private final Collection<String> queryParts;


    ExpansionQuery(final Collection<String> queryParts) {
        this.queryParts = queryParts;
    }


    public Collection<String> getQueryParts() {
        return queryParts;
    }


    public String getQuery() {
        return Joiner.on(",").join(queryParts);
    }
}
