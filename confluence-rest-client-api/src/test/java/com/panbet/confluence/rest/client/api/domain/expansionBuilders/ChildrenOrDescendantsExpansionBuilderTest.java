package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentType;
import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ChildrenOrDescendantsExpansionBuilderTest {
    @Test
    public void testBuild() {
        ChildrenOrDescendantsExpansionsBuilder builder = new ChildrenOrDescendantsExpansionsBuilder(
                Content.Expansions.CHILDREN);

        builder.addExpansion(ContentType.BLOG_POST);
        builder.addExpansion(ContentType.PAGE);

        builder.addExpansionQuery(new ContentExpansionsBuilder(ContentType.COMMENT.getType())
                .addExpansion("history")
                .addExpansion("body")
                .build());

        List<String> expectedQueryParts = Arrays.asList("children.comment.history", "children.comment.body", "children.blogpost",
                "children.page");
        String expectedQueryString = Joiner.on(",").join(expectedQueryParts);

        ExpansionQuery query = builder.build();

        assertEquals(expectedQueryParts, query.getQueryParts());
        assertEquals(expectedQueryString, query.getQuery());
    }
}
