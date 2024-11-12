package com.panbet.stash.rest.client.api.alternative.repo;


import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;

import java.io.Serializable;
import java.util.Optional;


public interface Project extends Serializable {
    String getKey();


    Long getId();


    String getName();


    Optional<String> getDescription();


    boolean isPublic();


    ProjectType getType();


    Optional<Link> getLink();


    ImmutableCollection<Self> getSelf();
}
