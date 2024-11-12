package com.panbet.stash.rest.client.api.alternative.commit;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.assertj.core.api.Assertions;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;


class StashParentJsonParserTest {
    private final String FILE_PATH = "com/panbet/stash/rest/client/api/alternative/commit/";

    private final JsonObjectParser<MinimalParent> STASH_PARENT_JSON_PARSER = new StashParentJsonParser();


    @Test
    void testCorrectJson() throws IOException, JSONException {
        JSONObject json = getJsonByName("parent.json");
        MinimalParent parent = STASH_PARENT_JSON_PARSER.parse(json);
        Assertions.assertThat(parent).isNotNull();
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