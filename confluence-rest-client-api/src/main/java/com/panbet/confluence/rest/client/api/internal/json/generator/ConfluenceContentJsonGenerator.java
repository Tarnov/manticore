package com.panbet.confluence.rest.client.api.internal.json.generator;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentBody;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.atlassian.jira.rest.client.internal.json.gen.JsonGenerator;
import com.panbet.confluence.rest.client.api.internal.json.ReferenceSerializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;
import java.util.Map;


public class ConfluenceContentJsonGenerator implements JsonGenerator<Content> {
    private static final ReferenceSerializer referenceSerializer = new ReferenceSerializer();


    @Override
    public JSONObject generate(final Content content) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        if (content.getId() != null) {
            jsonObject.put("id", content.getId().asLong());
        }

        if (content.getType() != null) {
            jsonObject.put("type", content.getType().getType());
        }

        jsonObject.putOpt("title", content.getTitle());

        if (content.getStatus() != null) {
            jsonObject.put("status", content.getStatus().getValue());
        }

        referenceSerializer.serializeSpaceReference(jsonObject, content.getSpaceRef(), "space");

        if (content.getAncestors().size() != 0) {
            jsonObject.put("ancestors", getListOfContentsJsonArray(content.getAncestors()));
        }

        if (content.getBody().size() != 0) {
            jsonObject.put("body", getContentBodyJson(content.getBody()));
        }

        return jsonObject;
    }


    private JSONArray getListOfContentsJsonArray(final List<Content> contents) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Content content : contents) {
            jsonArray.put(this.generate(content));
        }
        return jsonArray;
    }


    private JSONObject getContentBodyJson(final Map<ContentRepresentation, ContentBody> body) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (ContentRepresentation representation : body.keySet()) {
            ContentBody contentBody = body.get(representation);
            JSONObject keyJsonObject = new JSONObject();
            referenceSerializer.serializeContentReference(keyJsonObject, contentBody.getContentRef(), "content");
            keyJsonObject.put("value", contentBody.getValue());
            keyJsonObject.put("representation", contentBody.getRepresentation());
            jsonObject.put(representation.getRepresentation(), keyJsonObject);
        }
        return jsonObject;
    }

}
