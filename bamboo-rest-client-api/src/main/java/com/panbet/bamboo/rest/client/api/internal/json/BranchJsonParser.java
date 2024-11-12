package com.panbet.bamboo.rest.client.api.internal.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.bamboo.rest.client.api.domain.Branch;
import com.panbet.bamboo.rest.client.api.domain.BranchImpl;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class BranchJsonParser implements JsonObjectParser<Branch> {
    @Override
    public Branch parse(JSONObject json) throws JSONException {
        String description = json.getString("description");
        String shortName = json.getString("shortName");
        String shortKey = json.getString("shortKey");
        boolean enabled = json.getBoolean("enabled");
        String key = json.getString("key");
        String name = json.getString("name");

        return new BranchImpl(description, shortName, shortKey, enabled, key, name);
    }
}
