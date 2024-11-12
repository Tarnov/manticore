package com.panbet.bamboo.rest.client.api.domain;


import com.atlassian.util.concurrent.Promise;


public interface ProjectPlanRestClient {
    Promise<ProjectPlan> getProjectPlan(String projectKey, String buildKey);

    Promise<ProjectPlan> getProjectPlan(String projectKey, String buildKey, Iterable<Expands> expands);

    public enum Expands {
        BRANCHES,
        ACTIONS,
        STAGES,
        VARIABLE_CONTEXT
    }
}
