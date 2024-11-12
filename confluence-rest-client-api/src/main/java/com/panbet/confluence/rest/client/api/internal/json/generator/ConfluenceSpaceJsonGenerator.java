package com.panbet.confluence.rest.client.api.internal.json.generator;


import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.atlassian.confluence.api.model.content.FormattedBody;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.jira.rest.client.internal.json.gen.JsonGenerator;
import com.panbet.confluence.rest.client.api.internal.json.ReferenceSerializer;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Map;


public class ConfluenceSpaceJsonGenerator implements JsonGenerator<Space> {
    private static final ReferenceSerializer referenceSerializer = new ReferenceSerializer();


    @Override
    public JSONObject generate(final Space space) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", space.getId());
        jsonObject.putOpt("key", space.getKey());
        jsonObject.putOpt("name", space.getName());

        referenceSerializer.serializeIconReference(jsonObject, space.getIconRef(), "icon");

        if (space.getDescription().size() != 0) {
            jsonObject.put("description", getDescriptionJson(space.getDescription()));
        }

        referenceSerializer.serializeContentReference(jsonObject, space.getHomepageRef(), "homepage");

        jsonObject.putOpt("type", space.getType().getValue());

        return jsonObject;
    }


    private JSONObject getDescriptionJson(final Map<ContentRepresentation, FormattedBody> description) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (ContentRepresentation representation : description.keySet()) {
            FormattedBody body = description.get(representation);
            JSONObject keyJsonObject = new JSONObject();
            keyJsonObject.put("value", body.getValue());
            keyJsonObject.put("representation", body.getRepresentation());
            jsonObject.put(representation.getRepresentation(), keyJsonObject);
        }
        return jsonObject;
    }

}
