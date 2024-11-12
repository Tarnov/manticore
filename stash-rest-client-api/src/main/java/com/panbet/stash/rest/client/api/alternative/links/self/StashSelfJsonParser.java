package com.panbet.stash.rest.client.api.alternative.links.self;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashSelfJsonParser implements JsonObjectParser<Self> {
    @Override
    public Self parse(final JSONObject jsonObject) throws JSONException {
        final String rawHref = jsonObject.getString("href");

        return new SelfImpl(rawHref);
    }
}
