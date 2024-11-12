package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.Change;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class StashChangesJsonParser implements JsonObjectParser<Collection<Change>> {
    private static final StashChangeJsonParser STASH_CHANGE_JSON_PARSER = new StashChangeJsonParser();


    @Override
    public Collection<Change> parse(final JSONObject json) throws JSONException {
        final JSONArray jsonArray = json.getJSONArray("values");
        final Collection<Change> changes = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            changes.add(STASH_CHANGE_JSON_PARSER.parse(jsonArray.getJSONObject(i)));
        }

        return changes;
    }
}
