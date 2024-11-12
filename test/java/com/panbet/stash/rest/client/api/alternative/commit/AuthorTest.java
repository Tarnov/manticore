package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;


class AuthorTest {
    private final String name = "name";

    private final String emailAddress = "emailAddress";

    private final Integer id = 10;

    private final String displayName = "displayName";

    private final AuthorImpl.UserType type = AuthorImpl.UserType.NORMAL;

    private final Boolean active = false;

    private final String slug = "slug";

    private final Link link = mock(Link.class);

    private final ImmutableCollection.Builder<Self> linksBuilder = ImmutableList.builder();

    private final ImmutableCollection<Self> links = linksBuilder.build();


    @Test
    void testCorrectConstructor() {
        Assertions.assertThat(new AuthorImpl(name, emailAddress, id, displayName, type, active, slug, link, links)).isNotNull();
    }

    @Test
    void testNotLinksConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new AuthorImpl(name, emailAddress, id, displayName, type, active, slug, link, null));
    }
}