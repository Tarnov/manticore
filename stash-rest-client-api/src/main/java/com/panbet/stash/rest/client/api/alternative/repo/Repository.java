package com.panbet.stash.rest.client.api.alternative.repo;


import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.clone.Clone;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;

import java.io.Serializable;
import java.net.URI;
import java.util.Optional;


public interface Repository extends Serializable {
    String getSlug();


    Integer getId();


    String getName();


    String getScmId();


    State getState();


    String getStatusMessage();


    boolean isForkable();


    Project getProject();


    boolean isPublic();


    Optional<Link> getLink();


    Optional<URI> getCloneUrl();


    ImmutableCollection<Self> getSelf();


    ImmutableCollection<Clone> getClone();
}
