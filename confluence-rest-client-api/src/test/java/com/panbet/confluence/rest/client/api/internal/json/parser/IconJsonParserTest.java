package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.web.Icon;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;


public class IconJsonParserTest {
    private static final ConfluenceIconJsonParser iconParser = new ConfluenceIconJsonParser();


    @Test
    public void testParseIcon() throws JSONException {
        JSONObject json = new JSONObject(TestUtils.getIconJson());

        Icon parsedIcon = iconParser.parse(json);
        TestUtils.assertForIcon(parsedIcon);
    }

}
