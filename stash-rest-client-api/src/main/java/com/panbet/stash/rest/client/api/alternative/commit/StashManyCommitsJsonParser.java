package com.panbet.stash.rest.client.api.alternative.commit;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class StashManyCommitsJsonParser implements JsonObjectParser<Collection<Commit>> {
    private static final StashCommitJsonParser PARSER = new StashCommitJsonParser();


    @Override
    public Collection<Commit> parse(final JSONObject json) throws JSONException {
        final JSONArray jsonArray = json.getJSONArray("values");
        final ArrayList<Commit> result = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(PARSER.parse(jsonArray.getJSONObject(i)));
        }

        return result;
    }
}
