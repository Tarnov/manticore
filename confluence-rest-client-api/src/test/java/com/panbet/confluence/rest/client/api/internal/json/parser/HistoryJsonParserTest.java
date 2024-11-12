package com.panbet.confluence.rest.client.api.internal.json.parser;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;


public class HistoryJsonParserTest {
    private static final ConfluenceHistoryJsonParser historyParser = new ConfluenceHistoryJsonParser();


    @Test
    public void testParseHistory() throws JSONException {
        JSONObject jsonObject = new JSONObject(TestUtils.getHistoryJson());

        TestUtils.assertForHistory(historyParser.parse(jsonObject));
    }

}
