package com.panbet.stash.rest.client.api.alternative.jiraIntegration;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.panbet.stash.rest.client.api.alternative.commit.Commit;
import com.panbet.stash.rest.client.api.alternative.commit.StashCommitJsonParser;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;
import com.panbet.stash.rest.client.api.alternative.repo.StashRepoJsonParser;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class StashJiraIntegrationJsonParser implements JsonObjectParser<ImmutableCollection<JiraIntegrationCommit>> {
    private static final JsonObjectParser<Commit> STASH_COMMIT_JSON_PARSER = new StashCommitJsonParser();

    private static final JsonObjectParser<Repository> STASH_REPO_JSON_PARSER = new StashRepoJsonParser();


    @Override
    public ImmutableCollection<JiraIntegrationCommit> parse(final JSONObject json) throws JSONException {
        final JSONArray jsonArray = json.getJSONArray("values");
        final ImmutableCollection.Builder<JiraIntegrationCommit> commitsBuilder = ImmutableList.builder();
        final JiraIntegrationCommitBuilder jiraIntegrationCommitBuilder = new JiraIntegrationCommitBuilder();

        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject rawJson = (JSONObject) jsonArray.get(i);
            final JSONObject rawCommit = rawJson.getJSONObject("toCommit");
            final Commit commit = STASH_COMMIT_JSON_PARSER.parse(rawCommit);
            final JSONObject rawRepo = rawJson.getJSONObject("repository");
            final Repository repository = STASH_REPO_JSON_PARSER.parse(rawRepo);

            final JiraIntegrationCommit jiraIntegrationCommit = jiraIntegrationCommitBuilder
                    .setCommit(commit)
                    .setRepository(repository)
                    .build();

            commitsBuilder.add(jiraIntegrationCommit);
        }

        return commitsBuilder.build();
    }
}
