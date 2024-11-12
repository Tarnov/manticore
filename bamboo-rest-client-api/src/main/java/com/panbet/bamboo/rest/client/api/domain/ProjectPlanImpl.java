package com.panbet.bamboo.rest.client.api.domain;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collection;


public class ProjectPlanImpl implements ProjectPlan, Serializable {
    private String projectKey;
    private String projectName;
    private String shortName;
    private String shortKey;
    private String key;
    private String name;

    private Type projectType;

    private boolean enabled;
    private boolean favourite;
    private boolean active;
    private boolean building;

    private double averageBuildTimeInSeconds;

    private Collection<Action> actions;
    private Collection<Stage> stages;
    private Collection<Branch> branches;
    private Collection<VariableContext> variableContexts;


    public ProjectPlanImpl(String projectKey, String projectName, String shortName, String shortKey, String key,
                           String name, Type projectType, boolean enabled, boolean favourite, boolean active,
                           boolean building, double averageBuildTimeInSeconds,
                           @Nullable Collection<Action> actions,
                           @Nullable Collection<Stage> stages,
                           @Nullable Collection<Branch> branches,
                           @Nullable Collection<VariableContext> variableContexts) {
        this.projectKey = projectKey;
        this.projectName = projectName;
        this.shortName = shortName;
        this.shortKey = shortKey;
        this.key = key;
        this.name = name;
        this.projectType = projectType;
        this.enabled = enabled;
        this.favourite = favourite;
        this.active = active;
        this.building = building;
        this.averageBuildTimeInSeconds = averageBuildTimeInSeconds;
        this.actions = actions;
        this.stages = stages;
        this.branches = branches;
        this.variableContexts = variableContexts;
    }


    @Override
    public String getProjectKey() {
        return projectKey;
    }


    @Override
    public String getProjectName() {
        return projectName;
    }


    @Override
    public String getShortName() {
        return shortName;
    }


    @Override
    public String getShortKey() {
        return shortKey;
    }


    @Override
    public Type getProjectType() {
        return projectType;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }


    @Override
    public boolean isFavourite() {
        return favourite;
    }


    @Override
    public boolean isActive() {
        return active;
    }


    @Override
    public boolean isBuilding() {
        return building;
    }


    @Override
    public double getAverageBuildTimeInSeconds() {
        return averageBuildTimeInSeconds;
    }


    @Override
    public String getKey() {
        return key;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public Collection<Stage> getStages() {
        return stages;
    }


    @Override
    public Collection<Branch> getBranches() {
        return branches;
    }


    @Override
    public Collection<VariableContext> getVariableContext() {
        return variableContexts;
    }


    @Override
    public Collection<Action> getActions() {
        return actions;
    }
}
