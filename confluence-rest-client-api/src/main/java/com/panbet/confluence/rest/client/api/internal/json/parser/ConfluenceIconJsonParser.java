package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


//Finished
public class ConfluenceIconJsonParser implements JsonObjectParser<Icon> {
    @Override
    public Icon parse(final JSONObject json) throws JSONException {
        String path = json.optString("path");
        int width = json.optInt("width");
        int height = json.optInt("height");
        boolean isDefault = json.getBoolean("isDefault");
        return new Icon(path, width, height, isDefault);
    }
}
