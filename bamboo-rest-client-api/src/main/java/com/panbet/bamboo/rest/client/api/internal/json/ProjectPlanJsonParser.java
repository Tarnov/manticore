package com.panbet.bamboo.rest.client.api.internal.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.bamboo.rest.client.api.domain.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;


public class ProjectPlanJsonParser implements JsonObjectParser<ProjectPlan> {
    private BranchesJsonParser branchesJsonParser = new BranchesJsonParser();
    private BasicProjectPlanJsonParser basicProjectPlanJsonParser = new BasicProjectPlanJsonParser();


    @Override
    public ProjectPlan parse(JSONObject json) throws JSONException {
        String projectKey = json.getString("projectKey");
        String projectName = json.getString("projectName");
        boolean favourite = json.getBoolean("isFavourite");
        boolean active = json.getBoolean("isActive");
        boolean building = json.getBoolean("isBuilding");
        double averageBuildTimeInSeconds = json.getDouble("averageBuildTimeInSeconds");
        Collection<Action> actions = null;
        Collection<Stage> stages = null;
        Collection<VariableContext> variableContexts = null;
        Collection<Branch> branches = (Collection<Branch>) branchesJsonParser.parse(json.getJSONObject("branches"));

        BasicProjectPlan b = basicProjectPlanJsonParser.parse(json);

        return new ProjectPlanImpl(projectKey, projectName, b.getShortName(), b.getShortKey(), b.getKey(), b.getName(),
                b.getProjectType(), b.isEnabled(), favourite, active, building,
                averageBuildTimeInSeconds, actions, stages, branches, variableContexts);
    }
}
