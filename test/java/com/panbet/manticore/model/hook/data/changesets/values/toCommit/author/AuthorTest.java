package com.panbet.manticore.model.hook.data.changesets.values.toCommit.author;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class AuthorTest {
    private static final String name = "name";

    private static final String emailAddress = "emailAddress";


    @Test
    void testCorrectConstructor() {
        AuthorImpl author = new AuthorImpl(name, emailAddress);
        Assertions.assertThat(author.getName()).isEqualTo(name);
        Assertions.assertThat(author.getEmailAddress()).isEqualTo(emailAddress);
    }


    @Test
    void testNotNameConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new AuthorImpl(null, emailAddress));
    }


    @Test
    void testNotEmailAddressConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new AuthorImpl(name, null));
    }
}