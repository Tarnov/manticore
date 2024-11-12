package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.Diff;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class StashManyDiffsJsonParser implements JsonObjectParser<Collection<Diff>> {
    private static final Logger logger = LoggerFactory.getLogger(StashManyDiffsJsonParser.class);

    private static final StashDiffJsonParser stashDiffJsonParser = new StashDiffJsonParser();


    @Override
    public Collection<Diff> parse(final JSONObject json) throws JSONException {
        final JSONArray jsonArray = json.getJSONArray("diffs");
        final List<Diff> diffs = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                diffs.add(stashDiffJsonParser.parse(jsonArray.getJSONObject(i)));
            } catch (final JSONException e) {
                logger.warn("Error while parsing this JSON: {}", json.toString(), e);
            }
        }

        return diffs;
    }
}
