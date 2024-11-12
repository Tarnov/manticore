package com.panbet.manticore.oauth.entities;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TokenImplTest {
    private static final String TOKEN = "token";

    private static final String TOKEN_SECRET = "token_secret";

    private static final String TOKEN_MESSAGE = "token must be not empty";

    private static final String TOKEN_SECRET_MESSAGE = "tokenSecret must be not empty";


    @ParameterizedTest
    @MethodSource("paramsProvider")
    void testWrongParams(final String token, final String tokenSecret, final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class, () -> new TokenImpl(token, tokenSecret));
        assertEquals(message, t.getMessage());
    }


    private static Stream<Arguments> paramsProvider() {
        return Stream.of(Arguments.of(null, TOKEN_SECRET, TOKEN_MESSAGE),
                Arguments.of("", TOKEN_SECRET, TOKEN_MESSAGE),
                Arguments.of(TOKEN, null, TOKEN_SECRET_MESSAGE),
                Arguments.of(TOKEN, "", TOKEN_SECRET_MESSAGE));
    }


    @Test
    void testSuccess() {
        final TokenImpl token = new TokenImpl(TOKEN, TOKEN_SECRET);
        assertEquals(TOKEN, token.getToken());
        assertEquals(TOKEN_SECRET, token.getTokenSecret());
    }
}
