package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.people.Anonymous;
import com.atlassian.confluence.api.model.people.KnownUser;
import com.atlassian.confluence.api.model.people.Person;
import com.atlassian.confluence.api.model.people.UnknownUser;
import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


//Finished
public class ConfluencePersonJsonParser implements JsonObjectParser<Person> {
    private static final ConfluenceIconJsonParser iconJsonParser = new ConfluenceIconJsonParser();


    @Override
    public Person parse(final JSONObject json) throws JSONException {
        String type = json.getString("type");
        if (type.equals("anonymous")) {
            return new Anonymous(iconJsonParser.parse(json.getJSONObject("profilePicture")), "Anonymous");
        } else {
            return parseUser(json);
        }
    }


    private Person parseUser(final JSONObject json) throws JSONException {
        String type = json.getString("type");
        String username = json.getString("username");
        String displayName = json.getString("displayName");
        Icon icon = iconJsonParser.parse(json.getJSONObject("profilePicture"));
        if (type.equals("known")) {
            return new KnownUser(icon, username, displayName, "");
        } else {
            return new UnknownUser(icon, username, displayName, "");
        }
    }
}
