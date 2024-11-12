package com.panbet.manticore.util.bl.nimt.view;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;


class NimtIssueStatusBuilderTest {
    private static final URI self = UriBuilder.fromUri("self").build();

    private static final String name = "name";

    private static final Long id = 1L;

    private static final String description = "description";

    private static final URI iconUrl = UriBuilder.fromUri("iconUrl").build();


    @Test
    void testSuccess() {
        final NimtIssueStatus status = new NimtIssueStatusBuilder()
                .setSelf(self)
                .setDescription(description)
                .setIconUrl(iconUrl)
                .setId(id)
                .setName(name)
                .build();

        Assertions.assertThat(status.getSelf()).isEqualTo(self);
        Assertions.assertThat(status.getDescription()).isEqualTo(description);
        Assertions.assertThat(status.getIconUrl()).isEqualTo(iconUrl);
        Assertions.assertThat(status.getId()).isEqualTo(id);
        Assertions.assertThat(status.getName()).isEqualTo(name);
    }


    @Test
    void testSelfFail() {
        NimtIssueStatusBuilder builder = new NimtIssueStatusBuilder()
                .setDescription(description)
                .setIconUrl(iconUrl)
                .setId(id)
                .setName(name);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testDescriptionFail() {
        NimtIssueStatusBuilder builder = new NimtIssueStatusBuilder()
                .setSelf(self)
                .setIconUrl(iconUrl)
                .setId(id)
                .setName(name);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIconUrlFail() {
        NimtIssueStatusBuilder builder = new NimtIssueStatusBuilder()
                .setDescription(description)
                .setSelf(self)
                .setId(id)
                .setName(name);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIdFail() {
        NimtIssueStatusBuilder builder = new NimtIssueStatusBuilder()
                .setDescription(description)
                .setIconUrl(iconUrl)
                .setSelf(self)
                .setName(name);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNameFail() {
        NimtIssueStatusBuilder builder = new NimtIssueStatusBuilder()
                .setDescription(description)
                .setIconUrl(iconUrl)
                .setId(id)
                .setSelf(self);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}