package com.panbet.stash.rest.client.api.alternative.commit;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import org.assertj.core.api.Assertions;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;


class StashAuthorJsonParserTest {
    private final String FILE_PATH = "com/panbet/stash/rest/client/api/alternative/commit/";

    private final JsonObjectParser<MinimalAuthor> STASH_AUTHOR_JSON_PARSER = new StashAuthorJsonParser();


    @Test
    void testCorrectJson() throws IOException, JSONException {
        JSONObject json = getJsonByName("author.json");
        MinimalAuthor author = STASH_AUTHOR_JSON_PARSER.parse(json);
        Assertions.assertThat(author).isNotNull();
    }


    @Test
    void testNotName() throws IOException, JSONException {
        JSONObject json = getJsonByName("authorNotName.json");
        Assertions.assertThatExceptionOfType(JSONException.class)
                .isThrownBy(() -> STASH_AUTHOR_JSON_PARSER.parse(json));
    }


    @Test
    void testNotActive() throws IOException, JSONException {
        JSONObject json = getJsonByName("authorNotActive.json");
        MinimalAuthor author = STASH_AUTHOR_JSON_PARSER.parse(json);
        Assertions.assertThat(author).isNotNull();
    }


    @Test
    void testNotDisplayName() throws IOException, JSONException {
        JSONObject json = getJsonByName("authorNotDisplayName.json");
        Assertions.assertThatExceptionOfType(JSONException.class)
                .isThrownBy(() -> STASH_AUTHOR_JSON_PARSER.parse(json));
    }


    @Test
    void testNotEmail() throws IOException, JSONException {
        JSONObject json = getJsonByName("authorNotEmail.json");
        Assertions.assertThatExceptionOfType(JSONException.class)
                .isThrownBy(() -> STASH_AUTHOR_JSON_PARSER.parse(json));
    }


    @Test
    void testNotId() throws IOException, JSONException {
        JSONObject json = getJsonByName("authorNotId.json");
        Assertions.assertThatExceptionOfType(JSONException.class)
                .isThrownBy(() -> STASH_AUTHOR_JSON_PARSER.parse(json));
    }


    @Test
    void testNotSlug() throws IOException, JSONException {
        JSONObject json = getJsonByName("authorNotSlug.json");
        Assertions.assertThatExceptionOfType(JSONException.class)
                .isThrownBy(() -> STASH_AUTHOR_JSON_PARSER.parse(json));
    }


    @Test
    void testNotType() throws IOException, JSONException {
        JSONObject json = getJsonByName("authorNotType.json");
        Assertions.assertThatExceptionOfType(JSONException.class)
                .isThrownBy(() -> STASH_AUTHOR_JSON_PARSER.parse(json));
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