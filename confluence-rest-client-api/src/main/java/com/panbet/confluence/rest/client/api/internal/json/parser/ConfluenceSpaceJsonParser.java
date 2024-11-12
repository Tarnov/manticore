package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.atlassian.confluence.api.model.content.FormattedBody;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.api.model.content.SpaceType;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


//Finished
public class ConfluenceSpaceJsonParser implements JsonObjectParser<Space> {
    private static final ConfluenceIconJsonParser iconJsonParser = new ConfluenceIconJsonParser();

    private static final ConfluenceContentJsonParser contentJsonParser = new ConfluenceContentJsonParser();


    @Override
    public Space parse(final JSONObject json) throws JSONException {
        Space.SpaceBuilder builder = Space.builder();
        builder.id(json.getLong("id")).key(json.getString("key")).name(json.getString("name"));

        JSONObject iconJson = json.optJSONObject("icon");
        if (iconJson != null) {
            builder.icon(iconJsonParser.parse(iconJson));
        }

        JSONObject descriptionJson = json.optJSONObject("description");
        if (descriptionJson != null) {
            Map<ContentRepresentation, FormattedBody> description = parseDescription(descriptionJson);
            if (description.size() != 0) {
                builder.description(description);
            }
        }

        JSONObject homepageJson = json.optJSONObject("homepage");
        if (homepageJson != null) {
            builder.homepage(contentJsonParser.parse(homepageJson));
        }

        builder.type(SpaceType.forName(json.getString("type")));

        return builder.build();
    }


    @SuppressWarnings("unchecked")
    private Map<ContentRepresentation, FormattedBody> parseDescription(final JSONObject json) throws JSONException {
        Map<ContentRepresentation, FormattedBody> description = new HashMap<>();
        Iterator<String> keysIterator = json.keys();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            if (!key.startsWith("_")) {
                JSONObject keyJson = json.getJSONObject(key);
                description.put(ContentRepresentation.valueOf(key),
                        new FormattedBody(ContentRepresentation.valueOf(keyJson.optString("representation")),
                                keyJson.optString("value")));
            }
        }
        return description;
    }
}
