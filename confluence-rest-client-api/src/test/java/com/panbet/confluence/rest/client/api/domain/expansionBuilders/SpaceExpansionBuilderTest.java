package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.Space;
import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class SpaceExpansionBuilderTest {
    @Test
    public void testBuild() {
        SpaceExpansionsBuilder builder = new SpaceExpansionsBuilder();

        builder.addExpansion(Space.Expansions.DESCRIPTION);
        builder.addExpansion(Space.Expansions.ICON);

        builder.addExpansionQuery(new ContentExpansionsBuilder(Space.Expansions.HOMEPAGE)
                .addExpansion(Content.Expansions.BODY)
                .addExpansion(Content.Expansions.ANCESTORS)
                .addExpansionQuery(new SpaceExpansionsBuilder().addExpansion(Space.Expansions.ICON)
                        .build())
                .build());

        List<String> expectedQueryParts = Arrays.asList("space.homepage.space.icon", "space.homepage.body",
                "space.homepage.ancestors", "space.description", "space.icon");
        String expectedQueryString = Joiner.on(",").join(expectedQueryParts);


        ExpansionQuery query = builder.build();

        assertEquals(expectedQueryParts, query.getQueryParts());
        assertEquals(expectedQueryString, query.getQuery());
    }

}
