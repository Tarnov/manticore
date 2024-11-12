package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.content.History;
import com.atlassian.confluence.api.model.reference.Reference;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;


//Finished
//Except contentParent
public class ConfluenceHistoryJsonParser implements JsonObjectParser<History> {
    private static final ConfluenceVersionJsonParser versionJsonParser = new ConfluenceVersionJsonParser();

    private static final ConfluencePersonJsonParser personJsonParser = new ConfluencePersonJsonParser();


    @Override
    public History parse(final JSONObject json) throws JSONException {

        History.HistoryBuilder builder = History.builder();
        builder.latest(json.getBoolean("latest")).createdDate(new DateTime(json.get("createdDate")))
                .createdBy(personJsonParser.parse(json.getJSONObject("createdBy")));

        JSONObject lastUpdatedJson = json.optJSONObject("lastUpdated");
        if (lastUpdatedJson != null) {
            builder.lastUpdated(Reference.to(versionJsonParser.parse(lastUpdatedJson)));
        }

        JSONObject previousVersionJson = json.optJSONObject("previousVersion");
        if (previousVersionJson != null) {
            builder.previousVersion(Reference.to(versionJsonParser.parse(previousVersionJson)));
        }

        JSONObject nextVersionJson = json.optJSONObject("nextVersion");
        if (nextVersionJson != null) {
            builder.nextVersion(Reference.to(versionJsonParser.parse(nextVersionJson)));
        }

        return builder.build();
    }

}
