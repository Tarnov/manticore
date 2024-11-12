package com.panbet.confluence.rest.client.api.internal.json.parser;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;


public class VersionJsonParserTest {
    private static final ConfluenceVersionJsonParser versionParser = new ConfluenceVersionJsonParser();


    @Test
    public void testParseVersion() throws JSONException {
        JSONObject json = new JSONObject(TestUtils.getVersionJson());

        TestUtils.assertForVersion(versionParser.parse(json));
    }

}
