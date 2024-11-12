package com.panbet.stash.rest.client.api.alternative.commit;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashParentJsonParser implements JsonObjectParser<MinimalParent> {
    @Override
    public MinimalParent parse(final JSONObject rawParent) throws JSONException {
        return new MinimalParent(
                rawParent.getString("id"),
                rawParent.getString("displayId")
        );
    }
}
