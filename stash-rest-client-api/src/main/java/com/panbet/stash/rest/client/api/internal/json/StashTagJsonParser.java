package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.repository.Tag;
import com.panbet.stash.rest.client.api.domain.TagInfo;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashTagJsonParser implements JsonObjectParser<Tag> {
    @Override
    public Tag parse(final JSONObject json) throws JSONException {
        final String id = json.getString("id");
        final String displayId = json.getString("displayId");
        final String latestChangeset = json.getString("latestChangeset");
        final String hash = json.getString("hash");

        return new TagInfo(id, displayId, latestChangeset, hash);
    }
}
