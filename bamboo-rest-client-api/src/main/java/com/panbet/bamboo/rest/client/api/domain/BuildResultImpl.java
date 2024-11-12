package com.panbet.bamboo.rest.client.api.domain;


import org.joda.time.DateTime;


public class BuildResultImpl implements BuildResult {
    private final int number;

    private final BasicProjectPlan projectPlan;

    private final DateTime buildCompletedTime;

    private final BuildResultState state;

    private final LifeCycleState lifeCycleState;

    private final String vcsRevisionKey;


    public BuildResultImpl(final int number, final BasicProjectPlan projectPlan, final DateTime buildCompletedTime,
                           final BuildResultState state, final LifeCycleState lifeCycleState, final String vcsRevisionKey) {
        this.number = number;
        this.projectPlan = projectPlan;
        this.buildCompletedTime = buildCompletedTime;
        this.state = state;
        this.lifeCycleState = lifeCycleState;
        this.vcsRevisionKey = vcsRevisionKey;
    }


    @Override
    public DateTime getBuildCompletedTime() {
        return buildCompletedTime;
    }


    @Override
    public BasicProjectPlan getProjectPlan() {
        return projectPlan;
    }


    @Override
    public int getNumber() {
        return number;
    }


    @Override
    public BuildResultState getState() {
        return state;
    }


    @Override
    public LifeCycleState getLifeCycleState() {
        return lifeCycleState;
    }


    @Override
    public String getVcsRevisionKey() {
        return vcsRevisionKey;
    }
}
