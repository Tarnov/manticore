package com.panbet.manticore.oauth.headers.util;


import com.panbet.manticore.oauth.headers.methodParameters.MethodParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class KeyValueAppenderImplTest {
    private static final String KEY = "key";

    private static final String VALUE = "value";

    @Mock
    private MethodParameter parameter;

    @Mock
    private Escaper escaper;


    @BeforeEach
    void initMocks() {
        parameter = mock(MethodParameter.class);
        escaper = mock(Escaper.class);

        when(parameter.getKey()).thenReturn(KEY);
        when(parameter.getValue()).thenReturn(VALUE);

        when(escaper.escape(anyString())).then(returnsFirstArg());
    }


    @Test
    void testAppendForSignature() {
        final KeyValueAppender appender = new KeyValueAppenderImpl(escaper);

        final String expected = KEY + '=' + VALUE;
        final String result = appender.appendForSignature(parameter);

        verify(escaper, times(1)).escape(KEY);
        verify(escaper, times(1)).escape(VALUE);
        assertEquals(expected, result);
    }


    @Test
    void testAppendForHeaders() {
        when(escaper.escape(any())).then(returnsFirstArg());

        final KeyValueAppender appender = new KeyValueAppenderImpl(escaper);

        final String expected = KEY + "=\"" + VALUE + '"';
        final String result = appender.appendForHeader(parameter);

        verify(escaper, times(1)).escape(KEY);
        verify(escaper, times(1)).escape(VALUE);
        assertEquals(expected, result);
    }


    @Test
    void testInvalidParams() {
        final Throwable t = assertThrows(IllegalArgumentException.class, () -> new KeyValueAppenderImpl(null));
        assertEquals("escaper must be not null", t.getMessage());
    }
}
