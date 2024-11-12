package com.panbet.confluence.rest.client.api.domain;


import java.util.Map;
import java.util.Set;

public class RequestParams {
    private final Map<AvailableRequestParams, String> params;


    RequestParams(final Map<AvailableRequestParams, String> params) {
        this.params = params;
    }


    public Set<AvailableRequestParams> getParamsKeys() {
        return this.params.keySet();
    }


    public String getParamValue(final AvailableRequestParams paramKey) {
        return this.params.get(paramKey);
    }
}
