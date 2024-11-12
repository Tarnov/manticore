package com.panbet.bamboo.rest.client.api.internal.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.panbet.bamboo.rest.client.api.domain.Branch;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;


public class BranchesJsonParser implements JsonObjectParser<Iterable<Branch>> {
    private BranchJsonParser parser = new BranchJsonParser();

    @Override
    public Iterable<Branch> parse(JSONObject json) throws JSONException {
        Collection<Branch> branchCollection = null;
        if (!json.isNull("branch")) {
            JSONArray branches = json.getJSONArray("branch");
            branchCollection = new ArrayList<>(branches.length());
            for (int i = 0; i < branches.length(); i++) {
                branchCollection.add(parser.parse(branches.getJSONObject(i)));
            }
        }

        return branchCollection;
    }
}
