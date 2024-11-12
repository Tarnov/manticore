package com.panbet.confluence.rest.client.api.internal.json.parser;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;


public class SpaceJsonParserTest {
    private static final ConfluenceSpaceJsonParser spaceParser = new ConfluenceSpaceJsonParser();


    @Test
    public void testParseSpace() throws JSONException {
        JSONObject json = new JSONObject(TestUtils.getSpaceJson());

        TestUtils.assertForSpace(spaceParser.parse(json));
    }

}
