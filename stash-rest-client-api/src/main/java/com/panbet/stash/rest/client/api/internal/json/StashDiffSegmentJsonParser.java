package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.DiffLine;
import com.atlassian.stash.content.DiffSegment;
import com.atlassian.stash.content.DiffSegmentType;
import com.panbet.stash.rest.client.api.domain.DiffSegmentImpl;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StashDiffSegmentJsonParser implements JsonObjectParser<DiffSegment> {
    private static final StashDiffLineJsonParser STASH_DIFF_LINE_JSON_PARSER = new StashDiffLineJsonParser();


    @Override
    public DiffSegment parse(final JSONObject json) throws JSONException {
        final JSONArray linesJsonArray = json.getJSONArray("lines");
        final List<DiffLine> diffLines = new ArrayList<>(linesJsonArray.length());
        for (int i = 0; i < linesJsonArray.length(); i++) {
            diffLines.add(STASH_DIFF_LINE_JSON_PARSER.parse(linesJsonArray.getJSONObject(i)));
        }

        final DiffSegmentType type = DiffSegmentType.valueOf(json.getString("type"));

        final boolean truncated = json.getBoolean("truncated");

        return new DiffSegmentImpl(diffLines, type, truncated);
    }
}
