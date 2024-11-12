package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Optional;


public class AuthorImpl implements Author, Serializable {
    private final String name;

    private final String emailAddress;

    private final Integer id;

    private final String displayName;

    private final UserType type;

    private final boolean active;

    private final String slug;

    private final Link link;

    private final ImmutableCollection<Self> links;


    AuthorImpl(final String name, final String emailAddress, final Integer id, final String displayName,
               final UserType type, final boolean active, final String slug, final Link link,
               final ImmutableCollection<Self> links) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(emailAddress), "EmailAddress require not null");
        Preconditions.checkArgument(id != null, "id require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(displayName), "displayName require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(slug), "slug require not null");
        Preconditions.checkArgument(links != null, "Links require not null");

        this.name = name;
        this.emailAddress = emailAddress;
        this.id = id;
        this.displayName = displayName;
        this.type = type;
        this.active = active;
        this.slug = slug;
        this.link = link;
        this.links = links;
    }


    public String getDisplayName() {
        return displayName;
    }


    public Integer getId() {
        return id;
    }


    public UserType getType() {
        return type;
    }


    public boolean isActive() {
        return active;
    }


    public String getSlug() {
        return slug;
    }


    public String getEmailAddress() {
        return emailAddress;
    }


    public String getName() {
        return name;
    }


    public Optional<Link> getLink() {
        return Optional.ofNullable(link);
    }


    public ImmutableCollection<Self> getLinks() {
        return links;
    }


    public enum UserType {
        NORMAL,
        SERVICE
    }
}
