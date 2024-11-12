package com.panbet.stash.rest.client.api.alternative.commit;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.google.common.collect.ImmutableSet;
import org.assertj.core.api.Assertions;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;


class StashAttributesJsonParserTest {
    private final String FILE_PATH = "com/panbet/stash/rest/client/api/alternative/commit/";


    private final JsonObjectParser<ImmutableSet<String>> STASH_ATTRIBUTES_JSON_PARSER = new StashAttributesJsonParser();

    @Test
    void testCorrectJson() throws IOException, JSONException {
        JSONObject json = getJsonByName("commit.json");
        ImmutableSet<String> attributes = STASH_ATTRIBUTES_JSON_PARSER.parse(json);
        Assertions.assertThat(attributes).isNotEmpty();
    }

    @Test
    void testNotAttributesJson() throws IOException, JSONException {
        JSONObject json = getJsonByName("commitNotAttributes.json");
        ImmutableSet<String> attributes = STASH_ATTRIBUTES_JSON_PARSER.parse(json);
        Assertions.assertThat(attributes).isEmpty();
    }

    private JSONObject getJsonByName(final String fileName) throws IOException, JSONException {
        InputStream is = ClassLoader.getSystemResourceAsStream(FILE_PATH + fileName);
        StringBuilder stringBuilder = new StringBuilder();
        int data = is.read();
        while (data != -1) {
            stringBuilder.append((char) data);
            data = is.read();
        }

        return new JSONObject(stringBuilder.toString());
    }
}