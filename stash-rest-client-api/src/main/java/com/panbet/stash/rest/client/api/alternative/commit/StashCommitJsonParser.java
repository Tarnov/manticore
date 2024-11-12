package com.panbet.stash.rest.client.api.alternative.commit;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;


public class StashCommitJsonParser implements JsonObjectParser<Commit> {
    private final JsonObjectParser<MinimalParent> STASH_PARENT_JSON_PARSER = new StashParentJsonParser();

    private final JsonObjectParser<MinimalAuthor> STASH_AUTHOR_JSON_PARSER = new StashAuthorJsonParser();

    private final JsonObjectParser<ImmutableSet<String>> STASH_ATTRIBUTES_JSON_PARSER = new StashAttributesJsonParser();


    @Override
    public Commit parse(final JSONObject json) throws JSONException {
        final String id = json.getString("id");

        final String displayId = json.getString("displayId");

        final JSONObject rawAuthor = json.getJSONObject("author");

        final MinimalAuthor minimalAuthor = STASH_AUTHOR_JSON_PARSER.parse(rawAuthor);

        final Long timeStamp = json.getLong("authorTimestamp");

        final Date authorTimestamp = new Date(timeStamp);

        final String message = json.getString("message");

        final JSONArray rawParents = json.getJSONArray("parents");

        final ImmutableCollection.Builder<MinimalParent> parentsBuilder = ImmutableList.builder();
        for (int i = 0; i < rawParents.length(); i++) {
            final JSONObject rawParent = (JSONObject) rawParents.get(i);

            parentsBuilder.add(STASH_PARENT_JSON_PARSER.parse(rawParent));
        }

        final ImmutableSet<String> immutableJiraKeys = STASH_ATTRIBUTES_JSON_PARSER.parse(json);


        return new CommitImplBuilder()
                .setId(id)
                .setDisplayId(displayId)
                .setAuthor(minimalAuthor)
                .setAuthorTimestamp(authorTimestamp)
                .setMessage(message)
                .setParents(parentsBuilder.build())
                .setJiraKeys(immutableJiraKeys)
                .createCommitImpl();
    }

}
