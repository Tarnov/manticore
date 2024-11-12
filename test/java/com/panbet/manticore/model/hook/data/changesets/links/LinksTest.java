package com.panbet.manticore.model.hook.data.changesets.links;


import com.panbet.manticore.model.hook.data.changesets.links.self.Self;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;


class LinksTest {
    private static final Collection<Self> self = new ArrayList<>();


    @Test
    void testCorrectConstructor() {
        LinksImpl links = new LinksImpl(self);
        Assertions.assertThat(links.getSelf()).isEqualTo(self);
    }


    @Test
    void testNotSelfConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new LinksImpl(null));
    }
}