package com.panbet.http.request.executor;


import com.panbet.http.request.executor.headers.Header;
import com.panbet.http.request.executor.headers.HeaderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ObjectArrayArguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class HeaderImplTest {
    private static final String VALID_NAME = "name";

    private static final String VALID_VALUE = "value";

    private static final String INVALID_NAME_MESSAGE = "name must be not empty";

    private static final String INVALID_VALUE_MESSAGE = "value must be not empty";


    @ParameterizedTest
    @MethodSource(names = "paramsProvider")
    void testMissingParams(final String name, final String value, final String message) {
        Throwable t = assertThrows(IllegalArgumentException.class, () -> new HeaderImpl(name, value));
        assertEquals(message, t.getMessage());
    }


    static Stream<Arguments> paramsProvider() {
        return Stream.of(ObjectArrayArguments.create(null, VALID_VALUE, INVALID_NAME_MESSAGE),
                ObjectArrayArguments.create("", VALID_VALUE, INVALID_NAME_MESSAGE),
                ObjectArrayArguments.create(VALID_NAME, null, INVALID_VALUE_MESSAGE),
                ObjectArrayArguments.create(VALID_NAME, "", INVALID_VALUE_MESSAGE));
    }


    @Test
    void testCreationSuccess() {
        final Header header = new HeaderImpl(VALID_NAME, VALID_VALUE);

        assertEquals(VALID_NAME, header.getName());
        assertEquals(VALID_VALUE, header.getValue());
    }
}
