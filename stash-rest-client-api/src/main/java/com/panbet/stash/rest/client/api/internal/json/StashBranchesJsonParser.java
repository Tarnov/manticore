package com.panbet.stash.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.stash.rest.client.api.domain.BranchInfo;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class StashBranchesJsonParser implements JsonObjectParser<Collection<BranchInfo>> {
    @Override
    public Collection<BranchInfo> parse(final JSONObject json) throws JSONException {
        final JSONArray branchesJson = json.getJSONArray("values");
        final ArrayList<BranchInfo> branches = new ArrayList<>(branchesJson.length());
        for (int i = 0; i < branchesJson.length(); i++) {
            final JSONObject branch = (JSONObject) branchesJson.get(i);
            branches.add(new BranchInfo(branch.getString("id"), branch.getString("displayId"),
                    branch.getBoolean("isDefault")));
        }

        return branches;
    }
}
