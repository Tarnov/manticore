package com.panbet.stash.rest.client.api.domain;


import com.atlassian.util.concurrent.Promise;

import java.net.URI;
import java.util.Collection;


public interface ProjectRestClient {
    /**
     * Retrieves complete information about given project.
     *
     * @param key unique key of the project (usually 2+ characters)
     * @return complete information about given project
     * @throws com.atlassian.jira.rest.client.api.RestClientException in case of problems (connectivity, malformed messages, etc.)
     */
    Promise<StashProject> getProject(String key);

    /**
     * Retrieves complete information about given project.
     * Use this method rather than {@link ProjectRestClient#getProject(String)}
     * wheever you can, as this method is proof for potential changes of URI scheme used for exposing various
     * resources by JIRA REST API.
     *
     * @param projectUri URI to project resource (usually get from <code>self</code> attribute describing component elsewhere
     * @return complete information about given project
     * @throws com.atlassian.jira.rest.client.api.RestClientException in case of problems (connectivity, malformed messages, etc.)
     */
    Promise<StashProject> getProject(URI projectUri);

    /**
     * Returns all projects, which are visible for the currently logged in user. If no user is logged in, it returns the
     * list of projects that are visible when using anonymous access.
     *
     * @return projects which the currently logged user can see
     * @since v0.0.1
     */
    Promise<Collection<StashProject>> getAllProjects();

}
