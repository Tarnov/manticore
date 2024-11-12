package com.panbet.stash.rest.client.api.alternative.link;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashLinkJsonParser implements JsonObjectParser<Link> {
    @Override
    public Link parse(final JSONObject jsonObject) throws JSONException {
        final String url = jsonObject.getString("url");
        final String rel = jsonObject.getString("rel");
        return new LinkImpl(url, rel);
    }
}
