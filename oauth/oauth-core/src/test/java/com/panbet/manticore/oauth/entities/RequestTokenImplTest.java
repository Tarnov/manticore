package com.panbet.manticore.oauth.entities;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RequestTokenImplTest {
    private static final String TOKEN = "token";

    private static final String TOKEN_SECRET = "token_secret";

    private static final Boolean CALLBACK_CONFIRMED = true;

    private static final String TOKEN_MESSAGE = "token must be not empty";

    private static final String TOKEN_SECRET_MESSAGE = "tokenSecret must be not empty";

    private static final String CALLBACK_CONFIRMED_MESSAGE = "callbackConfirmed must be not null";


    @ParameterizedTest
    @MethodSource("paramsProvider")
    void testWrongParams(final String token, final String tokenSecret, final Boolean callbackConfirmed,
                                final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class, () -> new RequestTokenImpl(token, tokenSecret,
                callbackConfirmed));
        assertEquals(message, t.getMessage());
    }


    private static Stream<Arguments> paramsProvider() {
        return Stream.of(Arguments.of(null, TOKEN_SECRET, CALLBACK_CONFIRMED, TOKEN_MESSAGE),
                Arguments.of("", TOKEN_SECRET, CALLBACK_CONFIRMED, TOKEN_MESSAGE),
                Arguments.of(TOKEN, null, CALLBACK_CONFIRMED, TOKEN_SECRET_MESSAGE),
                Arguments.of(TOKEN, "", CALLBACK_CONFIRMED, TOKEN_SECRET_MESSAGE),
                Arguments.of(TOKEN, TOKEN_SECRET, null, CALLBACK_CONFIRMED_MESSAGE));
    }


    @Test
    void testSuccess() {
        final RequestTokenImpl token = new RequestTokenImpl(TOKEN, TOKEN_SECRET, CALLBACK_CONFIRMED);
        assertEquals(TOKEN, token.getToken());
        assertEquals(TOKEN_SECRET, token.getTokenSecret());
        assertEquals(CALLBACK_CONFIRMED, token.callbackConfirmed());
    }

}
