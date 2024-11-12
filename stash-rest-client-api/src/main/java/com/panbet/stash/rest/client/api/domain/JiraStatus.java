package com.panbet.stash.rest.client.api.domain;


import com.google.common.collect.ImmutableMap;

import java.util.Optional;


public class JiraStatus {
    public static final long TO_DO = 10011;

    public static final long OPEN = 1;

    public static final long REOPENED = 4;

    public static final long IN_PROGRESS = 3;

    public static final long IN_REVIEW = 10012;

    public static final long CONFIRM_TEXT = 10000;

    public static final long WAITING_FOR_REPLY = 10009;

    public static final long RESOLVED = 5;

    public static final long TESTING = 10005;

    public static final long IN_TEST_REVIEW = 10013;

    public static final long TESTED = 10008;

    public static final long READY_TO_MERGE = 10006;

    public static final long MERGED = 10007;

    public static final long CLOSED = 6;

    public static final long DONE = 10010;

    public static final long RESEARCH = 10114;

    public static final long BACKLOG = 10214;

    public static final long SELECTED_FOR_DEVELOPMENT = 10315;

    public static final long CHECK = 10414;

    public static final long PAUSED = 10514;

    public static final long IN_ACCEPTANCE = 13314;

    public static final long ACCEPTED = 13315;

    public static final long PRE_PROD_TESTING = 13316;

    public static final long READY_FOR_RELEASE = 13317;


    private static final ImmutableMap<Long, String> STATUS_IDS;

    static {
        STATUS_IDS = ImmutableMap.<Long, String>builder()
                .put(TO_DO, "To do")
                .put(OPEN, "Open")
                .put(REOPENED, "Reopened")
                .put(IN_PROGRESS, "In progress")
                .put(IN_REVIEW, "In review")
                .put(CONFIRM_TEXT, "Confirm text")
                .put(WAITING_FOR_REPLY, "Waiting for reply")
                .put(RESOLVED, "Resolved")
                .put(TESTING, "Testing")
                .put(IN_TEST_REVIEW, "In test review")
                .put(TESTED, "Tested")
                .put(READY_TO_MERGE, "Ready to merge")
                .put(MERGED, "Merged")
                .put(CLOSED, "Closed")
                .put(DONE, "Done")
                .put(RESEARCH, "Research")
                .put(BACKLOG, "Backlog")
                .put(SELECTED_FOR_DEVELOPMENT, "Selected for development")
                .put(CHECK, "Check")
                .put(PAUSED, "Paused")
                .put(IN_ACCEPTANCE, "In Acceptance")
                .put(PRE_PROD_TESTING, "Pre-Prod Testing")
                .put(READY_FOR_RELEASE, "Ready for Release")
                .put(ACCEPTED, "Accepted")
                .build();
    }

    public static Optional<String> getStatusById(final long statusId) {
        if (STATUS_IDS.containsKey(statusId)) {
            return Optional.of(STATUS_IDS.get(statusId));
        }

        return Optional.empty();
    }

}
