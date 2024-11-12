package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class ExpansionBuilder<T> {
    protected final Set<T> expansions = new LinkedHashSet<>();

    protected final Set<ExpansionQuery> expansionQueries = new LinkedHashSet<>();


    public ExpansionBuilder<T> addExpansion(final T expansion) {
        Preconditions.checkArgument(checkIfValidExpansion(expansion), "Non valid expansion");
        expansions.add(expansion);
        return this;
    }


    public ExpansionBuilder<T> addExpansionQuery(final ExpansionQuery query) {
        Preconditions.checkArgument(checkIfValidQuery(query), getInvalidQueryMessage());
        expansionQueries.add(query);
        return this;
    }


    public ExpansionQuery build() {
        Collection<String> ret = new ArrayList<>();
        String prefix = getPrefix().equals("") ? "" : getPrefix() + ".";
        expansionQueries.stream()
                .forEach(query -> ret.addAll(query.getQueryParts()
                        .stream()
                        .map(queryPart -> prefix + queryPart)
                        .collect(Collectors.toList())));

        expansions.forEach(expansion -> ret.add(prefix + expansion.toString()));

        return new ExpansionQuery(ret);
    }


    protected abstract boolean checkIfValidExpansion(final T expansion);


    protected abstract boolean checkIfValidQuery(final ExpansionQuery query);


    protected abstract String getInvalidQueryMessage();


    protected abstract String getPrefix();

}
