package com.panbet.bamboo.rest.client.api.internal.json;


import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import com.panbet.bamboo.rest.client.api.domain.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class BuildResultJsonParser implements JsonObjectParser<BuildResult> {
    private static final Logger logger = LoggerFactory.getLogger(BuildResultJsonParser.class);

    private final BasicProjectPlanJsonParser basicProjectPlanJsonParser = new BasicProjectPlanJsonParser();


    @Override
    public BuildResult parse(final JSONObject json) throws JSONException {
        try {
            final int number = json.getInt("number");
            final Optional<DateTime> buildCompletedTime = getBuildCompletedTime(json);
            final BasicProjectPlan plan = basicProjectPlanJsonParser.parse(json.getJSONObject("plan"));
            final BuildResultState state = BuildResultState.valueOf(json.getString("buildState").toUpperCase());
            final LifeCycleState lifeCycleState = LifeCycleState.valueOf(json.getString("lifeCycleState"));
            final String vcsRevisionKey = json.getString("vcsRevisionKey");

            return new BuildResultImpl(number, plan, buildCompletedTime.orElse(new DateTime()), state, lifeCycleState,
                    vcsRevisionKey);
        } catch (final Exception e) {
            logger.warn("Parsing json exception: {}", json.toString());

            throw e;
        }
    }


    private Optional<DateTime> getBuildCompletedTime(final JSONObject json) {
        final String buildCompletedTime = json.optString("buildCompletedTime");
        if (!buildCompletedTime.isEmpty()) {
            return Optional.of(JsonParseUtil.parseDateTimeOrDate(buildCompletedTime));
        } else {
            return Optional.empty();
        }
    }

}
