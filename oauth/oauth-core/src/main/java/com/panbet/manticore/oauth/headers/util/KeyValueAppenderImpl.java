package com.panbet.manticore.oauth.headers.util;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.methodParameters.MethodParameter;


public class KeyValueAppenderImpl implements KeyValueAppender {
    private static final char KEY_VALUE_SEPARATOR = '=';

    private static final char VALUE_WRAPPER_SYMBOL = '"';

    private final Escaper escaper;


    public KeyValueAppenderImpl(final Escaper escaper) {
        Preconditions.checkArgument(escaper != null, "escaper must be not null");

        this.escaper = escaper;
    }


    @Override
    public String appendForSignature(final MethodParameter parameter) {
        final String key = escaper.escape(parameter.getKey());
        final String value = escaper.escape(parameter.getValue());

        return new StringBuilder(key.length() + value.length() + 1)
                .append(key)
                .append(KEY_VALUE_SEPARATOR)
                .append(value)
                .toString();
    }


    @Override
    public String appendForHeader(final MethodParameter parameter) {
        final String key = escaper.escape(parameter.getKey());
        final String value = escaper.escape(parameter.getValue());

        return new StringBuilder(key.length() + value.length() + 3)
                .append(key)
                .append(KEY_VALUE_SEPARATOR)
                .append(VALUE_WRAPPER_SYMBOL)
                .append(value)
                .append(VALUE_WRAPPER_SYMBOL)
                .toString();
    }
}
