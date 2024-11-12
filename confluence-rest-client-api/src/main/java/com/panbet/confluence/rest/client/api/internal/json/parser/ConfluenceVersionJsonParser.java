package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.content.Version;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;


//Finished
public class ConfluenceVersionJsonParser implements JsonObjectParser<Version> {
    private static final ConfluencePersonJsonParser personJsonParser = new ConfluencePersonJsonParser();


    @Override
    public Version parse(final JSONObject json) throws JSONException {
        Version.VersionBuilder builder = Version.builder();

        builder.by(personJsonParser.parse(json.getJSONObject("by")))
                .when(new DateTime(json.get("when")))
                .message(json.optString("message"))
                .number(json.getInt("number"))
                .minorEdit(json.getBoolean("minorEdit"));

        return builder.build();
    }
}
