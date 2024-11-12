package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.repository.Tag;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class StashTagListJsonParser implements JsonObjectParser<Collection<Tag>> {
    private static final StashTagJsonParser PARSER = new StashTagJsonParser();


    @Override
    public Collection<Tag> parse(final JSONObject json) throws JSONException {
        final JSONArray jsonArray = json.getJSONArray("values");
        final Collection<Tag> stashRepos = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            stashRepos.add(PARSER.parse(jsonArray.getJSONObject(i)));
        }

        return stashRepos;
    }


}
