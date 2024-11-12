package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.content.Container;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class ConfluenceContainerJsonParser implements JsonObjectParser<Container> {
    private static final ConfluenceSpaceJsonParser spaceJsonParser = new ConfluenceSpaceJsonParser();

    private static final ConfluenceContentJsonParser contentJsonParser = new ConfluenceContentJsonParser();


    @Override
    public Container parse(final JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("title")) {
            return contentJsonParser.parse(jsonObject);
        } else {
            return spaceJsonParser.parse(jsonObject);
        }
    }

}
