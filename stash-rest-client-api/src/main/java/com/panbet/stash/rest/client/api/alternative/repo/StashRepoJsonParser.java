package com.panbet.stash.rest.client.api.alternative.repo;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.link.StashLinkJsonParser;
import com.panbet.stash.rest.client.api.alternative.links.clone.Clone;
import com.panbet.stash.rest.client.api.alternative.links.clone.StashCloneJsonParser;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;
import com.panbet.stash.rest.client.api.alternative.links.self.StashSelfJsonParser;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashRepoJsonParser implements JsonObjectParser<Repository> {
    private final JsonObjectParser<Project> STASH_PROJECT_JSON_PARSER = new StashProjectJsonParser();

    private final JsonObjectParser<Link> STASH_LINK_JSON_PARSER = new StashLinkJsonParser();

    private final JsonObjectParser<Self> STASH_SELF_JSON_PARSER = new StashSelfJsonParser();

    private final JsonObjectParser<Clone> STASH_CLONE_JSON_PARSER = new StashCloneJsonParser();


    @Override
    public Repository parse(final JSONObject json) throws JSONException {
        final String slug = json.getString("slug");
        final Integer id = json.getInt("id");
        final String name = json.getString("name");
        final String scmId = json.getString("scmId");
        State state = null;
        switch (json.getString("state")) {
            case "AVAILABLE":
                state = State.AVAILABLE;
                break;
            case "INITIALISATION_FAILED":
                state = State.INITIALISATION_FAILED;
                break;
            case "INITIALISING":
                state = State.INITIALISING;
                break;
        }
        final String statusMessage = json.getString("statusMessage");

        final boolean isForkable = json.getBoolean("forkable");

        final Project project = STASH_PROJECT_JSON_PARSER.parse(json.getJSONObject("project"));

        final boolean isPublic = json.getBoolean("public");

        final String cloneUrl = json.optString("cloneUrl");


        final JSONObject rawLink = json.optJSONObject("link");

        final Link link = rawLink == null ? null : STASH_LINK_JSON_PARSER.parse(rawLink);

        final JSONObject rawLinks = json.getJSONObject("links");


        final JSONArray rawSelf = rawLinks.getJSONArray("self");
        final ImmutableCollection<Self> self = ImmutableList.<Self>builder()
                .addAll(JsonParseUtil.parseJsonArray(rawSelf, STASH_SELF_JSON_PARSER))
                .build();


        final JSONArray rawClone = rawLinks.getJSONArray("clone");
        final ImmutableCollection<Clone> clone = ImmutableList.<Clone>builder()
                .addAll(JsonParseUtil.parseJsonArray(rawClone, STASH_CLONE_JSON_PARSER))
                .build();

        return new RepositoryImplBuilder().setSlug(slug).setId(id).setName(name).setScmId(scmId).setState(state)
                .setStatusMessage(statusMessage).setIsForkable(isForkable).setProject(project).setIsPublic(isPublic)
                .setLink(link).setCloneUrl(cloneUrl).setSelf(self).setClone(clone).createRepositoryImpl();
    }

}
