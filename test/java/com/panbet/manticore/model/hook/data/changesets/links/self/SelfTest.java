package com.panbet.manticore.model.hook.data.changesets.links.self;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;


class SelfTest {
    private static final URI href = URI.create("href");


    @Test
    void testCorrectConstructor() {
        SelfImpl self = new SelfImpl(href);
        Assertions.assertThat(self.getHref()).isEqualTo(href);
    }


    @Test
    void testNotUrlConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new SelfImpl(null));
    }
}