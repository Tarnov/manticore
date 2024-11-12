package com.panbet.confluence.rest.client.api.domain.expansionBuilders;


import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class BodyExpansionBuilderTest {
    private static final String BODY_PREFIX = "body.";


    @Test
    public void testBuild() {
        BodyExpansionsBuilder builder = new BodyExpansionsBuilder();
        builder.addExpansion(ContentRepresentation.EDITOR);
        builder.addExpansion(ContentRepresentation.PLAIN);
        builder.addExpansion(ContentRepresentation.RAW);

        List<String> expectedQueryParts = Arrays.asList(BODY_PREFIX + ContentRepresentation.EDITOR.toString(), BODY_PREFIX
                + ContentRepresentation.PLAIN.toString(), BODY_PREFIX + ContentRepresentation.RAW.toString());
        String expectedQueryString = Joiner.on(",").join(expectedQueryParts);

        ExpansionQuery query = builder.build();

        assertEquals(expectedQueryParts, query.getQueryParts());
        assertEquals(expectedQueryString, query.getQuery());
    }

}
