package com.panbet.http.request.executor.headers;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;


public class HeaderImpl implements Header {
    private final String name;

    private final String value;


    public HeaderImpl(final String name, final String value) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "name must be not empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(value), "value must be not empty");

        this.name = name;
        this.value = value;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getValue() {
        return value;
    }
}
