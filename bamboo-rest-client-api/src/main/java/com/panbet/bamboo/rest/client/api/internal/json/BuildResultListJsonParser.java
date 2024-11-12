package com.panbet.bamboo.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.bamboo.rest.client.api.domain.BuildResult;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BuildResultListJsonParser implements JsonObjectParser<List<BuildResult>> {
    private final BuildResultJsonParser buildResultJsonParser = new BuildResultJsonParser();

    @Override
    public List<BuildResult> parse(final JSONObject json) throws JSONException {
        final List<BuildResult> buildResults = new ArrayList<>();
        final JSONArray array = json.getJSONObject("results").getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            buildResults.add(buildResultJsonParser.parse(array.getJSONObject(i)));
        }

        return buildResults;
    }
}
