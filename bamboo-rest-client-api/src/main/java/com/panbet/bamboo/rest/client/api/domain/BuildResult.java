package com.panbet.bamboo.rest.client.api.domain;


import org.joda.time.DateTime;


public interface BuildResult {
    BasicProjectPlan getProjectPlan();

    DateTime getBuildCompletedTime();

    int getNumber();

    BuildResultState getState();

    LifeCycleState getLifeCycleState();

    String getVcsRevisionKey();
}
