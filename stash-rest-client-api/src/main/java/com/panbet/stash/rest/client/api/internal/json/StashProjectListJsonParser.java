package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.stash.rest.client.api.domain.StashProject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class StashProjectListJsonParser implements JsonObjectParser<Collection<StashProject>> {
    private static final StashProjectJsonParser PARSER = new StashProjectJsonParser();


    @Override
    public Collection<StashProject> parse(final JSONObject json) throws JSONException {
        final JSONArray jsonArray = json.getJSONArray("values");
        final Collection<StashProject> stashProjects = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            stashProjects.add(PARSER.parse(jsonArray.getJSONObject(i)));
        }

        return stashProjects;
    }
}
