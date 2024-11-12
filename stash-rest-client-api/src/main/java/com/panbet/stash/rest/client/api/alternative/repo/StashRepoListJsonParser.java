package com.panbet.stash.rest.client.api.alternative.repo;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;


public class StashRepoListJsonParser implements JsonObjectParser<Collection<Repository>> {
    private static final JsonObjectParser<Repository> PARSER = new StashRepoJsonParser();


    @Override
    public ImmutableCollection<Repository> parse(final JSONObject json) throws JSONException {
        final JSONArray jsonArray = json.getJSONArray("values");
        return ImmutableList.<Repository>builder()
                .addAll(JsonParseUtil.parseJsonArray(jsonArray, PARSER))
                .build();
    }


}
