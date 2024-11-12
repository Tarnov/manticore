package com.panbet.manticore.oauth.headers.signature.base;


import com.google.common.base.Preconditions;

import java.math.BigInteger;
import java.security.SecureRandom;


public class NonceGeneratorImpl implements NonceGenerator {
    private final SecureRandom random;


    public NonceGeneratorImpl(final SecureRandom random) {
        Preconditions.checkArgument(random != null, "random must be not null");

        this.random = random;
    }


    @Override
    public String generateNonce() {
        return new BigInteger(130, random).toString(32);
    }
}
