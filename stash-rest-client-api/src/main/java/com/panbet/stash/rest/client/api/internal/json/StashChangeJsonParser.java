package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.*;
import com.panbet.stash.rest.client.api.domain.CommitChange;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class StashChangeJsonParser implements JsonObjectParser<Change> {
    private static final Logger logger = LoggerFactory.getLogger(StashChangeJsonParser.class);

    private static final String EXECUTABLE = "executable";

    private static final String SRC_EXECUTABLE = "srcExecutable";


    @Override
    public Change parse(final JSONObject json) throws JSONException {
        final String contentId = json.getString("contentId");

        final JSONObject pathJson = json.getJSONObject("path");
        final JSONArray componentsJsonArray = pathJson.getJSONArray("components");
        final List<String> components = new ArrayList<>(componentsJsonArray.length());
        for (int i = 0; i < componentsJsonArray.length(); i++) {
            components.add(i, componentsJsonArray.getString(i));
        }
        final Path path = new SimplePath(components);

        Boolean executable = null;
        Boolean srcExecutable = null;
        try {
            executable = json.isNull(EXECUTABLE) ? null : json.getBoolean(EXECUTABLE);
            srcExecutable = json.isNull(SRC_EXECUTABLE) ? null : json.getBoolean(SRC_EXECUTABLE);
        } catch (final JSONException e) {
            logger.warn("Unexpected exception", e);
        }

        final int percentUnchanged = json.getInt("percentUnchanged");
        final ChangeType changeType = ChangeType.valueOf(json.getString("type"));
        final ContentTreeNode.Type nodeType = ContentTreeNode.Type.valueOf(json.getString("nodeType"));


        return new CommitChange(null, contentId, executable, nodeType, path, percentUnchanged,
                srcExecutable, null, changeType);
    }
}
