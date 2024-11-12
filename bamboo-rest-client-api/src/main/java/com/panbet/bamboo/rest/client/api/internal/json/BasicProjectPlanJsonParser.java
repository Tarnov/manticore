package com.panbet.bamboo.rest.client.api.internal.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.bamboo.rest.client.api.domain.BasicProjectPlan;
import com.panbet.bamboo.rest.client.api.domain.BasicProjectPlanImpl;
import com.panbet.bamboo.rest.client.api.domain.ProjectPlan;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class BasicProjectPlanJsonParser implements JsonObjectParser<BasicProjectPlan> {
    @Override
    public BasicProjectPlan parse(JSONObject json) throws JSONException {
        String shortName = json.getString("shortName");
        String shortKey = json.getString("shortKey");
        ProjectPlan.Type type = BasicProjectPlan.Type.valueOf(json.getString("type").toUpperCase());
        boolean enabled = json.getBoolean("enabled");
        String key = json.getString("key");
        String name = json.getString("name");

        return new BasicProjectPlanImpl(shortName, shortKey, type, enabled, key, name);
    }
}
