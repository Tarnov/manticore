package com.panbet.manticore.util.bl.nimt.view;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;


class NimtIssuePriorityBuilderTest {
    private static final URI iconUrl = UriBuilder.fromUri("iconUrl").build();

    private static final String name = "name";

    private static final Long id = 1L;


    @Test
    void testSuccess() {
        final NimtIssuePriority priority = new NimtIssuePriorityBuilder()
                .setIconUrl(iconUrl)
                .setId(id)
                .setName(name)
                .build();

        Assertions.assertThat(priority.getIconUrl()).isEqualTo(iconUrl);
        Assertions.assertThat(priority.getId()).isEqualTo(id);
        Assertions.assertThat(priority.getName()).isEqualTo(name);
    }


    @Test
    void testIconUrlFail() {
        NimtIssuePriorityBuilder builder = new NimtIssuePriorityBuilder()
                .setId(id)
                .setName(name);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIdFail() {
        NimtIssuePriorityBuilder builder = new NimtIssuePriorityBuilder()
                .setIconUrl(iconUrl)
                .setName(name);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNameFail() {
        NimtIssuePriorityBuilder builder = new NimtIssuePriorityBuilder()
                .setIconUrl(iconUrl)
                .setId(id);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}