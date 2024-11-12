package com.panbet.http.request.executor;


import com.panbet.http.request.executor.request.HttpMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ObjectArrayArguments;

import java.net.URI;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class EntityEnclosingHttpRequestTest {
    private static final HttpMethod METHOD = HttpMethod.GET;

    private static final String URI_STRING = "http://www.example.com";

    private static final URI URII = URI.create(URI_STRING);

    private static final String METHOD_MESSAGE = "method must be not null";

    private static final String URI_MESSAGE = "uri must be not null";


    /*@Test
    void testMissingParams1()
    {
        final Throwable t = assertThrows(IllegalArgumentException.class, () -> new EntityEnclosingHttpRequest(null));
        assertEquals(METHOD_MESSAGE, t.getMessage());
    }*/


    @Test
    void testSuccess1() {
        final EntityEnclosingHttpRequest request = new EntityEnclosingHttpRequest(METHOD);
        assertEquals(METHOD.getName(), request.getMethod());
    }


    @ParameterizedTest
    @MethodSource(names = "paramsProvider1")
    void testMissingParams2(final HttpMethod httpMethod, final URI uri, final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class,
                () -> new EntityEnclosingHttpRequest(httpMethod, uri));
        assertEquals(message, t.getMessage());
    }


    static Stream<Arguments> paramsProvider1() {
        return Stream.of(ObjectArrayArguments.create(null, URII, METHOD_MESSAGE),
                ObjectArrayArguments.create(METHOD, null, URI_MESSAGE));
    }


    @Test
    void testSuccess2() {
        final EntityEnclosingHttpRequest request = new EntityEnclosingHttpRequest(METHOD, URII);
        assertEquals(METHOD.getName(), request.getMethod());
        assertEquals(URII, request.getURI());
    }


    @ParameterizedTest
    @MethodSource(names = "paramsProvider2")
    void testMissingParams3(final HttpMethod httpMethod, final String uri, final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class,
                () -> new EntityEnclosingHttpRequest(httpMethod, uri));
        assertEquals(message, t.getMessage());
    }


    static Stream<Arguments> paramsProvider2() {
        return Stream.of(ObjectArrayArguments.create(null, URI_STRING, METHOD_MESSAGE),
                ObjectArrayArguments.create(METHOD, null, URI_MESSAGE),
                ObjectArrayArguments.create(METHOD, "http:^%*//www.example.com",
                        "Illegal character in opaque part at index 5: http:^%*//www.example.com"));
    }


    @Test
    void testSuccess3() {
        final EntityEnclosingHttpRequest request = new EntityEnclosingHttpRequest(METHOD, URI_STRING);
        assertEquals(METHOD.getName(), request.getMethod());
        assertEquals(URI.create(URI_STRING), request.getURI());
    }
}
