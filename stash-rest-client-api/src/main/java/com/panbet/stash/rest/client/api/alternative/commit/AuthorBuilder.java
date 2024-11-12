package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;


public class AuthorBuilder {
    private String name;

    private String emailAddress;

    private Integer id;

    private String displayName;

    private AuthorImpl.UserType type;

    private Boolean active;

    private String slug;

    private Link link;

    private ImmutableCollection<Self> links;


    public AuthorBuilder setName(final String name) {
        this.name = name;
        return this;
    }


    public AuthorBuilder setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }


    public AuthorBuilder setId(final Integer id) {
        this.id = id;
        return this;
    }


    public AuthorBuilder setDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }


    public AuthorBuilder setType(final AuthorImpl.UserType type) {
        this.type = type;
        return this;
    }


    public AuthorBuilder setActive(final Boolean active) {
        this.active = active;
        return this;
    }


    public AuthorBuilder setSlug(final String slug) {
        this.slug = slug;
        return this;
    }


    public AuthorBuilder setLink(final Link link) {
        this.link = link;
        return this;
    }


    public AuthorBuilder setLinks(final ImmutableCollection<Self> links) {
        this.links = links;
        return this;
    }


    public AuthorImpl createAuthor() {
        return new AuthorImpl(name, emailAddress, id, displayName, type, active, slug, link, links);
    }
}