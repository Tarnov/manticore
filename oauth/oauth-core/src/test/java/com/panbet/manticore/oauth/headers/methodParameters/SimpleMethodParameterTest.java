package com.panbet.manticore.oauth.headers.methodParameters;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SimpleMethodParameterTest {
    private static final String KEY = "key";

    private static final String VALUE = "value";

    private static final String KEY_MESSAGE = "key must be not empty";

    private static final String VALUE_MESSAGE = "value must be not null";


    @ParameterizedTest
    @MethodSource("paramsProvider")
    void testWrongParameters(final String key, final String value, final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class, () -> new SimpleMethodParameter(key, value));
        assertEquals(message, t.getMessage());
    }


    private static Stream<Arguments> paramsProvider() {
        return Stream.of(Arguments.of(null, VALUE, KEY_MESSAGE),
                Arguments.of("", VALUE, KEY_MESSAGE),
                Arguments.of(KEY, null, VALUE_MESSAGE));
    }


    @Test
    void testSuccess() {
        final SimpleMethodParameter simpleMethodParameter = new SimpleMethodParameter(KEY, VALUE);
        assertEquals(KEY, simpleMethodParameter.getKey());
        assertEquals(VALUE, simpleMethodParameter.getValue());
    }
}
