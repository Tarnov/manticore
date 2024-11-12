package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.Diff;
import com.atlassian.stash.content.DiffHunk;
import com.atlassian.stash.content.Path;
import com.atlassian.stash.content.SimplePath;
import com.panbet.stash.rest.client.api.domain.DiffImpl;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class StashDiffJsonParser implements JsonObjectParser<Diff> {
    private static final Logger logger = LoggerFactory.getLogger(StashDiffJsonParser.class);

    private static final String COMPONENTS = "components";

    private static final String BINARY = "binary";

    private static final String DESTINATION = "destination";

    private static final StashHunkJsonParser HUNK_JSON_PARSER = new StashHunkJsonParser();


    @Override
    public Diff parse(final JSONObject json) throws JSONException {
        Path destination = null;
        JSONArray componentsJsonArray;
        final List<String> components = new LinkedList<>();
        final String d = json.getString(DESTINATION);
        if (d != null) {
            componentsJsonArray = json.getJSONObject(DESTINATION).getJSONArray(COMPONENTS);
            for (int i = 0; i < componentsJsonArray.length(); i++) {
                components.add(i, componentsJsonArray.getString(i));
            }
            destination = new SimplePath(components);
        }


        final JSONArray hunks = json.getJSONArray("hunks");
        final List<DiffHunk> diffHunks = new ArrayList<>(hunks.length());
        for (int i = 0; i < hunks.length(); i++) {
            diffHunks.add(i, HUNK_JSON_PARSER.parse(hunks.getJSONObject(i)));
        }

        Path source = null;
        final String s = json.getString("source");
        if (s != null) {
            componentsJsonArray = json.getJSONObject("source").getJSONArray(COMPONENTS);
            for (int i = 0; i < componentsJsonArray.length(); i++) {
                components.add(i, componentsJsonArray.getString(i));
            }
            source = new SimplePath(components);
        }


        boolean binary = false;
        try {
            binary = !json.isNull(BINARY) && json.getBoolean(BINARY);
        } catch (final JSONException e) {
            logger.warn("Unexpected exception", e);
        }

        final boolean truncated = json.getBoolean("truncated");

        return new DiffImpl(destination, diffHunks, source, binary, truncated);
    }
}
