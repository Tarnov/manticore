package com.panbet.stash.rest.client.api.alternative.link;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LinkTest {
    @Test
    void testCorrectConstructor() {
        Assertions.assertThat(new LinkImpl("url", "rel")).isNotNull();
    }


    @Test
    void testNotUrlConstructor() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new LinkImpl(null, "rel"));
    }


    @Test
    void testNotRelConstructor() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new LinkImpl("url", null));
    }
}