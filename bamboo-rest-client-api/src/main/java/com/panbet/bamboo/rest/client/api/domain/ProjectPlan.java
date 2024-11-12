package com.panbet.bamboo.rest.client.api.domain;


import javax.annotation.Nullable;
import java.util.Collection;


public interface ProjectPlan extends BasicProjectPlan {
    String getProjectKey();

    String getProjectName();

    boolean isFavourite();

    boolean isActive();

    boolean isBuilding();

    double getAverageBuildTimeInSeconds();

    @Nullable
    Collection<Stage> getStages();

    @Nullable
    Collection<Branch> getBranches();

    @Nullable
    Collection<VariableContext> getVariableContext();

    @Nullable
    Collection<Action> getActions();

}
