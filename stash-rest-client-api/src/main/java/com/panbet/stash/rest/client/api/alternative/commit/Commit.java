package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.collect.ImmutableSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


public interface Commit extends Serializable {
    String getId();


    String getDisplayId();


    MinimalAuthor getMinimalAuthor();


    Date getAuthorTimestamp();


    String getMessage();


    Collection<MinimalParent> getParents();


    ImmutableSet<String> getJiraKeys();
}
