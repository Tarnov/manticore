package com.panbet.confluence.rest.client.api.internal.json.parser;


import com.atlassian.confluence.api.model.people.Person;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;


public class PersonJsonParserTest {
    private static final ConfluencePersonJsonParser personParser = new ConfluencePersonJsonParser();


    @Test
    public void testParseAnonymousPerson() throws JSONException {
        JSONObject json = new JSONObject(TestUtils.getAnonymousPersonJson());

        Person person = personParser.parse(json);
        TestUtils.assertForAnonymous(person);
    }


    @Test
    public void testParseKnownPerson() throws JSONException {
        JSONObject json = new JSONObject(TestUtils.getKnownPersonJson());

        Person person = personParser.parse(json);
        TestUtils.assertForKnown(person);
    }
}
