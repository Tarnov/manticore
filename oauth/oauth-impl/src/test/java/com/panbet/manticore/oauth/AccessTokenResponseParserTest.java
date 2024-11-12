package com.panbet.manticore.oauth;


import com.panbet.manticore.oauth.entities.Token;
import com.panbet.manticore.oauth.parsers.AccessTokenResponseParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AccessTokenResponseParserTest {
    private static final String TOKEN = "token";

    private static final String TOKEN_KEY = "oauth_token";

    private static final String TOKEN_SECRET = "tokenSecret";

    private static final String TOKEN_SECRET_KEY = "oauth_token_secret";

    private static final String BASE_STRING = TOKEN_KEY + '=' + TOKEN + '&' + TOKEN_SECRET_KEY + '=' + TOKEN_SECRET;

    private static final String MISSING_TOKEN_MESSSAGE = "Parameter 'oauth_token' is missing.";

    private static final String MISSING_TOKEN_SECRET_MESSAGE = "Parameter 'oauth_token_secret' is missing.";

    private static final String MISSING_VALUE_MESSAGE = "No value found for parameter ";

    private static final String FAILURE_MESSSAGE = "Failed to parse response. ";

    private AccessTokenResponseParser responseParser = new AccessTokenResponseParser();


    @ParameterizedTest
    @ValueSource(strings = {BASE_STRING,
            BASE_STRING + "&test=test",
            "test=test&" + BASE_STRING})
    void testParseToken(final String input) {
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        final Token accessToken = responseParser.parseResponse(inputStream);

        assertEquals(TOKEN, accessToken.getToken());
        assertEquals(TOKEN_SECRET, accessToken.getTokenSecret());
    }


    @ParameterizedTest
    @MethodSource("paramsProvider")
    void testInvalidParams(final String input, final String message) {
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

        final Throwable t = assertThrows(IllegalStateException.class, () -> responseParser.parseResponse(inputStream));
        assertEquals(FAILURE_MESSSAGE, t.getMessage());
        assertEquals(message, t.getCause().getMessage());
    }


    private static Stream<Arguments> paramsProvider() {
        return Stream.of(Arguments.of(TOKEN_KEY + "=" + TOKEN, MISSING_TOKEN_SECRET_MESSAGE),
                Arguments.of(TOKEN_SECRET_KEY + "=" + TOKEN_SECRET, MISSING_TOKEN_MESSSAGE),
                Arguments.of(TOKEN_KEY, MISSING_VALUE_MESSAGE + TOKEN_KEY),
                Arguments.of(TOKEN_KEY + '=' + TOKEN + '&' + TOKEN_SECRET_KEY,
                        MISSING_VALUE_MESSAGE + TOKEN_SECRET_KEY),
                Arguments.of("", MISSING_TOKEN_MESSSAGE),
                Arguments.of("test&" + BASE_STRING, MISSING_VALUE_MESSAGE + "test"),
                Arguments.of(BASE_STRING + "&test", MISSING_VALUE_MESSAGE + "test"));
    }
}
