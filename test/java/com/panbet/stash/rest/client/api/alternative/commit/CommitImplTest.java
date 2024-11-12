package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.mock;


class CommitImplTest {
    private final String id = "id";

    private final String displayId = "displayId";

    private static final MinimalAuthor author = createAuthor();

    private final Date authorTimestamp = new Date(1447677376000L);

    private final String message = "message";

    private final Collection<MinimalParent> parents = new ArrayList<>();

    private final ImmutableSet<String> jiraKeys = ImmutableSet.<String>builder().build();


    @Test
    void testCorrectConstructor() {
        Assertions.assertThat(new CommitImpl(id, displayId, author, authorTimestamp, message, parents, jiraKeys)).isNotNull();
    }


    @Test
    void testNotIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CommitImpl(null, displayId, author, authorTimestamp, message, parents, jiraKeys));
    }


    @Test
    void testNotDisplayIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CommitImpl(id, null, author, authorTimestamp, message, parents, jiraKeys));
    }


    @Test
    void testNotAuthorConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CommitImpl(id, displayId, null, authorTimestamp, message, parents, jiraKeys));
    }


    @Test
    void testNotAuthorTimestampConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CommitImpl(id, displayId, author, null, message, parents, jiraKeys));
    }


    @Test
    void testNotMessageConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CommitImpl(id, displayId, author, authorTimestamp, null, parents, jiraKeys));
    }


    @Test
    void testNotParentsConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CommitImpl(id, displayId, author, authorTimestamp, message, null, jiraKeys));
    }


    @Test
    void testNotJiraKeysConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CommitImpl(id, displayId, author, authorTimestamp, message, parents, null));
    }


    private static MinimalAuthor createAuthor() {
        final String name = "name";
        final String emailAddress = "emailAddress";
        final Integer id = 10;
        final String displayName = "displayName";
        final AuthorImpl.UserType type = AuthorImpl.UserType.NORMAL;
        final Boolean active = true;
        final String slug = "slug";
        final Link link = mock(Link.class);
        final ImmutableCollection.Builder<Self> linksBuilder = ImmutableList.builder();
        final ImmutableCollection<Self> links = linksBuilder.build();
        final Author author = new AuthorImpl(name, emailAddress, id, displayName, type, active, slug, link, links);

        return new MinimalAuthorImpl(name, emailAddress, active, author);
    }
}