package com.panbet.manticore.oauth.headers.methodParameters;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;


public class SimpleMethodParameter implements MethodParameter {
    private final String key;

    private final String value;


    public SimpleMethodParameter(final String key, final String value) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key must be not empty");
        Preconditions.checkArgument(value != null, "value must be not null");

        this.key = key;
        this.value = value;
    }


    @Override
    public String getKey() {
        return key;
    }


    @Override
    public String getValue() {
        return value;
    }
}
