package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


public class MinimalAuthorImpl implements MinimalAuthor, Serializable {
    private final String name;

    private final String emailAddress;

    private final boolean active;

    private final Author author;


    MinimalAuthorImpl(final String name, final String emailAddress, final boolean active, final Author author) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name require not null");
        Preconditions.checkState(!(author == null && active), "author require not null with active");

        if (active) {
            Preconditions.checkArgument(StringUtils.isNotBlank(emailAddress),
                    "EmailAddress require not blank if active");
        }

        this.name = name;
        this.emailAddress = emailAddress;
        this.active = active;
        this.author = author;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getEmailAddress() {
        return emailAddress == null ? "" : emailAddress;
    }


    @Override
    public boolean isActive() {
        return active;
    }


    @Override
    public Author getAuthor() {
        if (!active) {
            throw new IllegalStateException("The author can be obtained only if it is active");
        }

        return author;
    }
}
