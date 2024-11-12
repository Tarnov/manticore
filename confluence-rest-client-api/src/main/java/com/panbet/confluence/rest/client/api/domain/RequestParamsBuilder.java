package com.panbet.confluence.rest.client.api.domain;


import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RequestParamsBuilder {
    private final Map<AvailableRequestParams, String> params = new HashMap<>();


    public RequestParamsBuilder type(final String type) {
        Preconditions.checkArgument(StringUtils.isNotBlank(type), "Type require not blank");
        params.put(AvailableRequestParams.TYPE, type);
        return this;
    }


    public RequestParamsBuilder spaceKey(final String spaceKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(spaceKey), "Space key require not blank");
        params.put(AvailableRequestParams.SPACEKEY, spaceKey);
        return this;
    }


    public RequestParamsBuilder title(final String title) {
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "Title require not blank");
        params.put(AvailableRequestParams.TITLE, title);
        return this;
    }


    public RequestParamsBuilder postingDay(final Date date) {
        Preconditions.checkArgument(date != null, "Posting day require valid date");
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        params.put(AvailableRequestParams.POSTINGDAY, format.format(date));
        return this;
    }


    public RequestParamsBuilder start(final int start) {
        Preconditions.checkArgument(start >= 0, "Start require non negative value");
        params.put(AvailableRequestParams.START, Integer.toString(start));
        return this;
    }


    public RequestParamsBuilder limit(final int limit) {
        Preconditions.checkArgument(limit >= 1, "Limit require positive value");
        params.put(AvailableRequestParams.LIMIT, Integer.toString(limit));
        return this;
    }


    public RequestParams build() {
        return new RequestParams(params);
    }
}
