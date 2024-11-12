package com.panbet.stash.rest.client.api.alternative.repo;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.link.StashLinkJsonParser;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;
import com.panbet.stash.rest.client.api.alternative.links.self.StashSelfJsonParser;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashProjectJsonParser implements JsonObjectParser<Project> {
    private final JsonObjectParser<Link> STASH_LINK_JSON_PARSER = new StashLinkJsonParser();

    private final JsonObjectParser<Self> STASH_SELF_JSON_PARSER = new StashSelfJsonParser();


    @Override
    public Project parse(final JSONObject json) throws JSONException {
        final String key = json.getString("key");
        final Long id = JsonParseUtil.getOptionalLong(json, "id");
        final String name = JsonParseUtil.getOptionalString(json, "name");
        final String description = JsonParseUtil.getOptionalString(json, "description");
        final boolean isPublic = json.getBoolean("public");

        final ProjectType projectType =
                json.getString("type").equals("NORMAL") ? ProjectType.NORMAL : ProjectType.PERSONAL;

        final JSONObject rawLink = json.optJSONObject("link");
        final Link link = rawLink == null ? null : STASH_LINK_JSON_PARSER.parse(rawLink);

        final JSONObject rawLinks = json.getJSONObject("links");
        final JSONArray rawSelf = rawLinks.getJSONArray("self");


        final ImmutableCollection<Self> self = ImmutableList.<Self>builder()
                .addAll(JsonParseUtil.parseJsonArray(rawSelf, STASH_SELF_JSON_PARSER))
                .build();


        return new ProjectBuilder()
                .setKey(key)
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setIsPublic(isPublic)
                .setProjectType(projectType)
                .setLink(link)
                .setSelf(self)
                .createProject();
    }


}
