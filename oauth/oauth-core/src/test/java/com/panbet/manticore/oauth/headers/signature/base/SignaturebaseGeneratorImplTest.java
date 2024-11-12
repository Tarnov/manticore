package com.panbet.manticore.oauth.headers.signature.base;


import com.panbet.manticore.oauth.headers.methodParameters.MethodParameter;
import com.panbet.manticore.oauth.headers.util.Escaper;
import com.panbet.manticore.oauth.headers.util.KeyValueAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class SignaturebaseGeneratorImplTest {
    private static final String ESCAPER_MESSAGE = "escaper must be not null";

    private static final String KVAPPENDER_MESSAGE = "appender must be not null";

    private static final String HTTP_METHOD = "GET";

    private static final String URI_PATH = "http://www.example.com/test";

    private static final String URI_PARAM = "testParam=param";

    private static final URI REQUEST_URI = URI.create(URI_PATH + "?" + URI_PARAM);

    private Escaper escaper;

    private KeyValueAppender kVAppender;

    private MethodParameter testParam;

    private List<MethodParameter> params = new ArrayList<>();


    @BeforeEach
    void initMocks() {
        escaper = mock(Escaper.class);
        kVAppender = mock(KeyValueAppender.class);
        testParam = mock(MethodParameter.class);
        when(testParam.getKey()).thenReturn("key");
        when(testParam.getValue()).thenReturn("value");
    }


    @Test
    void testBaseGeneration() {
        params.addAll(Arrays.asList(testParam, testParam));

        final String appendResult = "appended=param";
        when(escaper.escape(any())).then(returnsFirstArg());
        when(kVAppender.appendForSignature(any())).thenReturn(appendResult);

        final SignatureBaseGenerator signatureBaseGenerator = new SignatureBaseGeneratorImpl(escaper, kVAppender);
        final String resultBase = signatureBaseGenerator.generateSignatureBase(HTTP_METHOD, REQUEST_URI, params);

        final String expectedParams = appendResult + '&' + appendResult + '&' + URI_PARAM;
        final String expectedBase = HTTP_METHOD + "&" + URI_PATH + "&" + expectedParams;

        verify(kVAppender, times(2)).appendForSignature(testParam);
        verify(escaper, times(1)).escape(HTTP_METHOD);
        verify(escaper, times(1)).escape(URI_PATH);
        verify(escaper, times(1)).escape(expectedParams);
        assertEquals(resultBase, expectedBase);
    }


    @ParameterizedTest
    @MethodSource("paramsProvider")
    void testInvalidParams(final Escaper escaper, final KeyValueAppender kVAppender, final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class,
                () -> new SignatureBaseGeneratorImpl(escaper, kVAppender));
        assertEquals(message, t.getMessage());
    }


    private static Stream<Arguments> paramsProvider() {
        final Escaper escaper = Mockito.mock(Escaper.class);
        final KeyValueAppender kvAppender = Mockito.mock(KeyValueAppender.class);

        return Stream.of(Arguments.of(null, kvAppender, ESCAPER_MESSAGE),
                Arguments.of(escaper, null, KVAPPENDER_MESSAGE));
    }
}
