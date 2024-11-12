package com.panbet.bamboo.rest.client.api.internal;

import com.atlassian.util.concurrent.ThreadFactories;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BambooConfiguredExecutor {
    public static int THREADS = 200;
    private static final String THREAD_PREFIX = "jiraRestClient";

    public static ExecutorService getExecutor() {
        return Executors.newFixedThreadPool(THREADS, ThreadFactories.namedThreadFactory(THREAD_PREFIX + "-callbacks"));
    }
}
