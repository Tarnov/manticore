package com.panbet.stash.rest.client.api;


import com.panbet.stash.rest.client.api.domain.*;

import java.io.Closeable;
import java.io.IOException;


public interface StashRestClient extends Closeable {
    String PROJECTS_URI_PREFIX = "projects";

    String REPO_URI_PREFIX = "repos";


    /**
     * @return the package com.panbet.stash.rest.client.api handling project metadata
     */
    ProjectRestClient getProjectClient();

    /**
     * @return the package com.panbet.stash.rest.client.api handling repo metadata
     */
    ReposRestClient getReposClient();

    /**
     * @return the package com.panbet.stash.rest.client.api handling commit metadata
     */
    CommitsRestClient getCommitRestClient();


    BranchRestClient getBranchRestClient();


    JiraIntegrationRestClient getJiraIntegrationRestClient();


    TagRestClient getTagRestClient();

    /**
     * Destroys this instance of Stash Rest Client.
     *
     * @throws IOException
     */
    void close() throws IOException;
}
