package com.panbet.stash.rest.client.api.alternative.commit;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.google.common.collect.ImmutableSet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashAttributesJsonParser implements JsonObjectParser<ImmutableSet<String>> {
    private String JIRA_KEY_ATTRIBUTE_NAME = "jira-key";


    @Override
    public ImmutableSet<String> parse(final JSONObject json) throws JSONException {
        final ImmutableSet.Builder<String> jiraKeysBuilder = ImmutableSet.builder();
        if (!json.isNull("attributes")) {
            final JSONObject rawAttributes = json.getJSONObject("attributes");
            final JSONArray rawJiraKeys = rawAttributes.getJSONArray(JIRA_KEY_ATTRIBUTE_NAME);
            for (int i = 0; i < rawJiraKeys.length(); i++) {
                if (!rawJiraKeys.isNull(i)) {
                    jiraKeysBuilder.add(rawJiraKeys.getString(i));
                }
            }
        }


        return jiraKeysBuilder
                .build();
    }
}
