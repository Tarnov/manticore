package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.*;
import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ContentExpansionBuilderTest {
    @Test
    public void testBuildSimpleExpansions() {
        ContentExpansionsBuilder builder = new ContentExpansionsBuilder();
        builder.addExpansion(Content.Expansions.ANCESTORS);
        builder.addExpansion(Content.Expansions.DESCENDANTS);
        builder.addExpansion(Content.Expansions.BODY);

        List<String> expectedQueryParts = Arrays.asList(Content.Expansions.ANCESTORS, Content.Expansions.DESCENDANTS,
                Content.Expansions.BODY);
        String expectedQueryString = Joiner.on(",").join(expectedQueryParts);

        ExpansionQuery query = builder.build();

        assertEquals(expectedQueryParts, query.getQueryParts());
        assertEquals(expectedQueryString, query.getQuery());
    }


    @Test
    public void testBuildNestedExpansions() {
        ContentExpansionsBuilder builder = new ContentExpansionsBuilder();
        builder.addExpansionQuery(new BodyExpansionsBuilder().addExpansion(ContentRepresentation.RAW)
                .addExpansion(ContentRepresentation.PLAIN).build());
        builder.addExpansionQuery(new HistoryExpansionsBuilder().addExpansion(History.Expansions.PREVIOUS).build());
        builder.addExpansion(Content.Expansions.ANCESTORS);

        List<String> expectedQueryParts = Arrays.asList("body.raw", "body.plain", "history.previousVersion",
                "ancestors");
        String expectedQueryString = Joiner.on(",").join(expectedQueryParts);

        ExpansionQuery query = builder.build();

        assertEquals(expectedQueryParts, query.getQueryParts());
        assertEquals(expectedQueryString, query.getQuery());
    }


    @Test
    public void testBuildManyDifferentExpansions() {
        ContentExpansionsBuilder builder = new ContentExpansionsBuilder();

        builder.addExpansion(Content.Expansions.VERSION).addExpansion(Content.Expansions.DESCENDANTS);
        builder.addExpansionQuery(
                        new SpaceExpansionsBuilder()
                                .addExpansion(Space.Expansions.ICON)
                                .addExpansionQuery(
                                        new ContentExpansionsBuilder(Space.Expansions.HOMEPAGE)
                                                .addExpansion(Content.Expansions.BODY)
                                                .addExpansionQuery(
                                                        new HistoryExpansionsBuilder().addExpansion(History.Expansions.NEXT)
                                                                .addExpansion(History.Expansions.PREVIOUS).build()).build())
                                .build())
                .addExpansionQuery(new BodyExpansionsBuilder().addExpansion(ContentRepresentation.PLAIN).build())
                .addExpansionQuery(new HistoryExpansionsBuilder().addExpansion(History.Expansions.LATEST).build())
                .addExpansionQuery(
                        new SpaceExpansionsBuilder(Content.Expansions.CONTAINER).addExpansion(Space.Expansions.ICON)
                                .build())
                .addExpansionQuery(
                        new ChildrenOrDescendantsExpansionsBuilder(Content.Expansions.CHILDREN).addExpansionQuery(
                                new ContentExpansionsBuilder(ContentType.PAGE.getType()).addExpansion(
                                        Content.Expansions.VERSION).build()).build())
                .addExpansionQuery(
                        new ContentExpansionsBuilder(Content.Expansions.ANCESTORS)
                                .addExpansion(Content.Expansions.CHILDREN).addExpansion(Content.Expansions.DESCENDANTS)
                                .build());

        List<String> expectedQueryParts = Arrays.asList("space.homepage.history.nextVersion",
                "space.homepage.history.previousVersion", "space.homepage.body", "space.icon", "body.plain",
                "history.lastUpdated", "container.icon", "children.page.version", "ancestors.children",
                "ancestors.descendants", "version", "descendants");
        String expectedQueryString = Joiner.on(",").join(expectedQueryParts);

        ExpansionQuery query = builder.build();

        assertEquals(expectedQueryParts, query.getQueryParts());
        assertEquals(expectedQueryString, query.getQuery());
    }

}
