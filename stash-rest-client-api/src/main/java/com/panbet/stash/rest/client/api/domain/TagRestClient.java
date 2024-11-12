package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.repository.Tag;
import com.atlassian.util.concurrent.Promise;

import java.util.Collection;


public interface TagRestClient {
    Promise<Collection<Tag>> getTags(final String project, final String repo);

    Promise<Collection<Tag>> getTags(final String filterText, final String project, final String repo);

    Promise<Collection<Tag>> getTags(final OrderBy orderBy, final String project, final String repo);

    Promise<Collection<Tag>> getTags(final String filterText, final OrderBy orderBy, final String project,
                                     final String repo);

    enum OrderBy {
        ALPHABETICAL, MODIFICATION
    }
}
