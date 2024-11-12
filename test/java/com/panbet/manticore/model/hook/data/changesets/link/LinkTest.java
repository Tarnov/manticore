package com.panbet.manticore.model.hook.data.changesets.link;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LinkTest {
    private static final String url = "url";

    private static final String rel = "rel";


    @Test
    void testCorrectConstructor() {
        LinkImpl link = new LinkImpl(url, rel);

        Assertions.assertThat(link.getUrl()).isEqualTo(url);
        Assertions.assertThat(link.getRel()).isEqualTo(rel);
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