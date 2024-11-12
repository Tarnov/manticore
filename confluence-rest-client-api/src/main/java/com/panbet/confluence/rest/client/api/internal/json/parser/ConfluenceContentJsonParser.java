package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.content.*;
import com.atlassian.confluence.api.model.content.id.ContentId;
import com.atlassian.confluence.api.model.pagination.PageResponse;
import com.atlassian.confluence.api.model.pagination.PageResponseImpl;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.*;


// Except operations, metadata, extensions
public class ConfluenceContentJsonParser implements JsonObjectParser<Content> {
    private static final ConfluenceSpaceJsonParser spaceJsonParser = new ConfluenceSpaceJsonParser();

    private static final ConfluenceHistoryJsonParser historyJsonParser = new ConfluenceHistoryJsonParser();

    private static final ConfluenceVersionJsonParser versionJsonParser = new ConfluenceVersionJsonParser();

    private static final ConfluenceContentListJsonParser contentListJsonParser = new ConfluenceContentListJsonParser();

    private static final ConfluenceContainerJsonParser containerJsonParser = new ConfluenceContainerJsonParser();


    @Override
    public Content parse(final JSONObject jsonObject) throws JSONException {
        Content.ContentBuilder builder = Content.builder();

        builder.id(ContentId.valueOf(jsonObject.getString("id")))
                .type(ContentType.valueOf(jsonObject.getString("type"))).title(jsonObject.getString("title"));

        String contentStatus = jsonObject.optString("status");
        if (contentStatus != null) {
            builder.status(ContentStatus.valueOf(contentStatus));
        }

        JSONObject spaceJson = jsonObject.optJSONObject("space");
        if (spaceJson != null) {
            builder.space(spaceJsonParser.parse(spaceJson));
        }

        JSONObject historyJson = jsonObject.optJSONObject("history");
        if (historyJson != null) {
            builder.history(historyJsonParser.parse(historyJson));
        }

        JSONObject versionJson = jsonObject.optJSONObject("version");
        if (versionJson != null) {
            builder.version(versionJsonParser.parse(versionJson));
        }

        JSONArray ancestorsJsons = jsonObject.optJSONArray("ancestors");
        if (ancestorsJsons != null && ancestorsJsons.length() != 0) {
            List<Content> ancestors = new ArrayList<>();
            for (int i = 0; i < ancestorsJsons.length(); i++) {
                ancestors.add(this.parse(ancestorsJsons.getJSONObject(i)));
            }
            builder.ancestors(ancestors);
        }

        JSONObject childrenJson = jsonObject.optJSONObject("children");
        if (childrenJson != null) {
            builder.children(parseChildrenOrDescendants(childrenJson));
        }

        JSONObject descendantsJson = jsonObject.optJSONObject("descendants");
        if (descendantsJson != null) {
            builder.descendants(parseChildrenOrDescendants(descendantsJson));
        }

        JSONObject containerJson = jsonObject.optJSONObject("container");
        if (containerJson != null) {
            builder.container(containerJsonParser.parse(containerJson));
        }

        JSONObject bodyJson = jsonObject.optJSONObject("body");
        if (bodyJson != null) {
            Map<ContentRepresentation, ContentBody> body = parseContentBody(bodyJson);
            if (body.size() != 0) {
                builder.body(body);
            }
        }

        return builder.build();
    }


    @SuppressWarnings("unchecked")
    private Map<ContentRepresentation, ContentBody> parseContentBody(JSONObject json) throws JSONException {
        Map<ContentRepresentation, ContentBody> body = new HashMap<>();
        Iterator<String> keysIterator = json.keys();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            if (!key.startsWith("_")) {
                JSONObject keyJson = json.getJSONObject(key);
                JSONObject contentJSON = keyJson.optJSONObject("content");
                if (contentJSON != null) {
                    body.put(ContentRepresentation.valueOf(key),
                            new ContentBody(ContentRepresentation.valueOf(keyJson.getString("representation")),
                                    keyJson.getString("value"), this.parse(contentJSON)));
                } else {
                    body.put(ContentRepresentation.valueOf(key),
                            new ContentBody(ContentRepresentation.valueOf(keyJson.getString("representation")),
                                    keyJson.getString("value")));
                }
            }
        }
        return body;
    }


    @SuppressWarnings("unchecked")
    private Map<ContentType, PageResponse<Content>> parseChildrenOrDescendants(JSONObject json) throws JSONException {
        Map<ContentType, PageResponse<Content>> ret = new HashMap<>();

        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (!key.startsWith("_")) {
                ret.put(ContentType.valueOf(key), new PageResponseImpl.Builder<Content>()
                        .addAll(contentListJsonParser.parse(json.getJSONObject(key))).build());
            }
        }

        return ret;
    }
}
