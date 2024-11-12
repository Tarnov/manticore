package com.panbet.stash.rest.client.api.alternative.links.self;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SelfTest {
    @Test
    void testCorrectConstructor() {
        Assertions.assertThat(new SelfImpl("href")).isNotNull();
    }


    @Test
    void testNotUrlConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new SelfImpl(null));
    }
}