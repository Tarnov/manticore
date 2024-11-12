package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.DiffHunk;
import com.atlassian.stash.content.DiffSegment;
import com.panbet.stash.rest.client.api.domain.DiffHunkImpl;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StashHunkJsonParser implements JsonObjectParser<DiffHunk> {
    private static final StashDiffSegmentJsonParser STASH_DIFF_SEGMENT_JSON_PARSER = new StashDiffSegmentJsonParser();


    @Override
    public DiffHunk parse(final JSONObject json) throws JSONException {
        final int destinationLine = json.getInt("destinationLine");
        final int destinationSpan = json.getInt("destinationSpan");
        final int sourceLine = json.getInt("sourceLine");
        final int sourceSpan = json.getInt("sourceSpan");

        final JSONArray segmentsJsonArray = json.getJSONArray("segments");
        final List<DiffSegment> diffSegments = new ArrayList<>(segmentsJsonArray.length());
        for (int i = 0; i < segmentsJsonArray.length(); i++) {
            diffSegments.add(STASH_DIFF_SEGMENT_JSON_PARSER.parse(segmentsJsonArray.getJSONObject(i)));
        }

        final boolean truncated = json.getBoolean("truncated");

        return new DiffHunkImpl(destinationLine, destinationSpan, diffSegments, sourceLine, sourceSpan, truncated);
    }
}
