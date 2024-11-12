package com.panbet.bamboo.rest.client.api.internal.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.bamboo.rest.client.api.domain.BambooProject;
import com.panbet.bamboo.rest.client.api.domain.ProjectLink;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;

public class ProjectJsonParser implements JsonObjectParser<Iterable<BambooProject>> {
    @Override
    public Iterable<BambooProject> parse(JSONObject json) throws JSONException {
        JSONObject rawProject = json.getJSONObject("projects");
        ArrayList<BambooProject> bambooProjects = new ArrayList<>(rawProject.getInt("size"));
        JSONArray rawProjectArray = rawProject.getJSONArray("project");
        for (int i = 0; i < rawProjectArray.length(); i++) {
            JSONObject projectJSON = (JSONObject) rawProjectArray.get(i);
            String key = projectJSON.getString("key");
            String name = projectJSON.getString("name");
            JSONObject linkJSON = projectJSON.getJSONObject("link");
            ProjectLink link = new ProjectLink(linkJSON.getString("href"), linkJSON.getString("rel"));
            BambooProject bambooProject = new BambooProject();
            bambooProject.setKey(key);
            bambooProject.setName(name);
            bambooProject.setLink(link);
            bambooProjects.add(bambooProject);
        }

        return bambooProjects;
    }
}
