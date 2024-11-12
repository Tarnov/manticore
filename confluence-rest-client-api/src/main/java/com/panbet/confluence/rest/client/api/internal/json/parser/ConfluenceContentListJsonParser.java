package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class ConfluenceContentListJsonParser implements JsonObjectParser<Collection<Content>> {
    private static final ConfluenceContentJsonParser contentJsonParser = new ConfluenceContentJsonParser();


    @Override
    public Collection<Content> parse(final JSONObject jsonObject) throws JSONException {
        Collection<Content> contents = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.length(); i++) {
            contents.add(contentJsonParser.parse(jsonArray.getJSONObject(i)));
        }
        return contents;
    }
}
