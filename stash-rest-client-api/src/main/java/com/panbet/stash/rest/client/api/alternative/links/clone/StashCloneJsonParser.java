package com.panbet.stash.rest.client.api.alternative.links.clone;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashCloneJsonParser implements JsonObjectParser<Clone> {
    @Override
    public Clone parse(final JSONObject jsonObject) throws JSONException {
        final String rawHref = jsonObject.getString("href");
        final String rawName = jsonObject.getString("name");

        return new CloneImpl(rawHref, rawName);
    }
}
