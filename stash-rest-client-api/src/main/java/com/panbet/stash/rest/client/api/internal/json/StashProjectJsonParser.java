package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import com.atlassian.stash.project.ProjectType;
import com.panbet.stash.rest.client.api.domain.StashProject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashProjectJsonParser implements JsonObjectParser<StashProject> {
    @Override
    public StashProject parse(final JSONObject json) throws JSONException {
        final boolean isPublic = json.getBoolean("public");
        final ProjectType projectType =
                json.getString("type").equals("NORMAL") ? ProjectType.NORMAL : ProjectType.PERSONAL;
        final String key = json.getString("key");
        final Long id = JsonParseUtil.getOptionalLong(json, "id");
        final String name = JsonParseUtil.getOptionalString(json, "name");
        final Boolean isPersonal = json.isNull("isPersonal") ? null : json.getBoolean("isPersonal");
        String description = JsonParseUtil.getOptionalString(json, "description");
        if ("".equals(description)) {
            description = null;
        }

        return new StashProject(key, id, name, isPublic, description, projectType, isPersonal);
    }


}
