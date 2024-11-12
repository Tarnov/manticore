package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.History;
import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class HistoryExpansionsBuilderTest {
    @Test
    public void testBuild() {
        HistoryExpansionsBuilder builder = new HistoryExpansionsBuilder();

        builder.addExpansion(History.Expansions.LATEST).addExpansion(History.Expansions.NEXT);

        List<String> expectedQueryParts = Arrays.asList("history.lastUpdated", "history.nextVersion");
        String expectedQueryString = Joiner.on(",").join(expectedQueryParts);

        ExpansionQuery query = builder.build();

        assertEquals(expectedQueryParts, query.getQueryParts());
        assertEquals(expectedQueryString, query.getQuery());
    }

}
