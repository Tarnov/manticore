package com.panbet.manticore.oauth.headers.signature.base;


import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class NonceGeneratorImplTest {
    @Test
    void testInvalidParams() {
        final Throwable t = assertThrows(IllegalArgumentException.class, () -> new NonceGeneratorImpl(null));
        assertEquals("random must be not null", t.getMessage());
    }


    @Test
    void testSuccess() {
        new NonceGeneratorImpl(new SecureRandom());
    }
}
