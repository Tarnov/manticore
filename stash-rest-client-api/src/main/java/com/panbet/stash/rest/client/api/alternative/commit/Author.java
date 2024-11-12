package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;

import java.util.Optional;


public interface Author {
    String getName();

    String getEmailAddress();

    boolean isActive();

    String getDisplayName();

    Integer getId();

    AuthorImpl.UserType getType();

    String getSlug();

    Optional<Link> getLink();

    ImmutableCollection<Self> getLinks();
}
