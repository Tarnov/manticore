package com.panbet.manticore.oauth.headers.signature.base;


import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.methodParameters.MethodParameter;
import com.panbet.manticore.oauth.headers.util.Escaper;
import com.panbet.manticore.oauth.headers.util.KeyValueAppender;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class SignatureBaseGeneratorImpl implements SignatureBaseGenerator {
    private static final char SIGNATURE_PARAMETERS_SEPARATOR = '&';

    private final Escaper escaper;

    private final KeyValueAppender appender;


    public SignatureBaseGeneratorImpl(final Escaper escaper, final KeyValueAppender appender) {
        Preconditions.checkArgument(escaper != null, "escaper must be not null");
        Preconditions.checkArgument(appender != null, "appender must be not null");

        this.escaper = escaper;
        this.appender = appender;
    }


    @Override
    public String generateSignatureBase(final String httpMethod, final URI requestUri,
                                        final List<MethodParameter> parameters) {
        final String normalizedUri = getNormalizedUri(requestUri);

        final List<String> joinedKeyValues = parameters.stream()
                .map(appender::appendForSignature)
                .collect(Collectors.toList());
        final List<String> queryParams = parseQueryParams(requestUri);
        joinedKeyValues.addAll(queryParams);

        Collections.sort(joinedKeyValues);

        return createDataForSignature(httpMethod, normalizedUri, joinedKeyValues);
    }


    private String getNormalizedUri(final URI requestUri) {
        try {
            return new URI(requestUri.getScheme(), requestUri.getAuthority(), requestUri.getPath(), null,
                    null).toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to create normalized uri");
        }
    }


    private List<String> parseQueryParams(final URI requestUri) {
        return requestUri.getQuery() == null ? Collections.emptyList() :
                Arrays.stream(requestUri.getQuery().split("&"))
                        .map(param -> param.contains("=") ? param : (param + '='))
                        .collect(Collectors.toList());
    }


    private String createDataForSignature(final String method, final String normalizedUri, final List<String> params) {
        final String escapedMethod = escaper.escape(method);
        final String escapedUri = escaper.escape(normalizedUri);
        final String escapedParams = escaper.escape(Joiner.on(SIGNATURE_PARAMETERS_SEPARATOR).join(params));

        return Joiner.on(SIGNATURE_PARAMETERS_SEPARATOR).join(escapedMethod, escapedUri, escapedParams);
    }
}
