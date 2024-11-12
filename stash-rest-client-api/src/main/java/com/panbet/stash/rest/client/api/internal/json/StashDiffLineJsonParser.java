package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.stash.content.ConflictMarker;
import com.atlassian.stash.content.DiffLine;
import com.panbet.stash.rest.client.api.domain.DiffLineImpl;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;


public class StashDiffLineJsonParser implements JsonObjectParser<DiffLine> {
    @Override
    public DiffLine parse(final JSONObject json) throws JSONException {
        final List<Long> commentIds = null;
        final ConflictMarker conflictMarker = null;

        final int destination = json.optInt("destination");
        final int source = json.getInt("source");

        final String line = json.getString("line");

        final boolean truncated = json.getBoolean("truncated");
        final boolean conflicting = false;

        return new DiffLineImpl(commentIds, conflictMarker, destination, line, source, conflicting, truncated);
    }
}
