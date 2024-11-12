package com.panbet.confluence.rest.client.api.internal.json.generator;


import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.jira.rest.client.internal.json.gen.JsonGenerator;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class ConfluenceIconJsonGenerator implements JsonGenerator<Icon> {
    @Override
    public JSONObject generate(final Icon icon) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("path", icon.getPath());
        jsonObject.put("width", icon.getWidth());
        jsonObject.put("height", icon.getHeight());
        jsonObject.put("isDefault", icon.getIsDefault());

        return jsonObject;
    }
}
