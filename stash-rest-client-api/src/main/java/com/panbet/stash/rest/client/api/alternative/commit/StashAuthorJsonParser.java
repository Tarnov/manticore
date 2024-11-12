package com.panbet.stash.rest.client.api.alternative.commit;


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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StashAuthorJsonParser implements JsonObjectParser<MinimalAuthor> {
    private static final Logger logger = LoggerFactory.getLogger(StashAuthorJsonParser.class);

    private final JsonObjectParser<Link> STASH_LINK_JSON_PARSER = new StashLinkJsonParser();

    private final JsonObjectParser<Self> STASH_SELF_JSON_PARSER = new StashSelfJsonParser();


    @Override
    public MinimalAuthor parse(final JSONObject rawAuthor) throws JSONException {
        try {
            final boolean active = !rawAuthor.isNull("active") && rawAuthor.getBoolean("active");
            final String name = rawAuthor.getString("name");
            final String emailAddress = active ? rawAuthor.getString("emailAddress") : null;
            final Author author = active ? getAuthor(rawAuthor, name, emailAddress, true) : null;

            return new MinimalAuthorImpl(name, emailAddress, active, author);
        } catch (final Exception e) {
            logger.warn("Parsing error, json: {}", rawAuthor);

            throw e;
        }
    }


    private Author getAuthor(final JSONObject rawAuthor, final String name, final String emailAddress,
                             final boolean active) throws JSONException {
        final JSONObject rawLink = rawAuthor.optJSONObject("link");
        final Link link = rawLink == null ? null : STASH_LINK_JSON_PARSER.parse(rawLink);


        final JSONObject rawLinks = rawAuthor.getJSONObject("links");
        final JSONArray rawSelf = rawLinks.getJSONArray("self");

        final ImmutableCollection<Self> links = ImmutableList.<Self>builder()
                .addAll(JsonParseUtil.parseJsonArray(rawSelf, STASH_SELF_JSON_PARSER))
                .build();

        return new AuthorBuilder()
                .setName(name)
                .setEmailAddress(emailAddress)
                .setId(rawAuthor.getInt("id"))
                .setDisplayName(rawAuthor.getString("displayName"))
                .setType(AuthorImpl.UserType.valueOf(rawAuthor.getString("type")))
                .setActive(active)
                .setSlug(rawAuthor.getString("slug"))
                .setLink(link)
                .setLinks(links)
                .createAuthor();
    }
}
