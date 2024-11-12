package com.panbet.confluence.rest.client.api.internal.json.parser;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;


public class ContentJsonParserTest {
    private static final ConfluenceContentJsonParser contentParser = new ConfluenceContentJsonParser();


    @Test
    public void testParseContent() throws JSONException {
        JSONObject json = new JSONObject(TestUtils.getContentJson());

        TestUtils.assertForContent(contentParser.parse(json));
    }

}
