package com.panbet.manticore.oauth.headers.signature.rsa;


/**
 * Encapsulates RSA-SHA1 signing algorithm.
 */
public interface RsaSha1Signer {
    /**
     * Uses RSA-SHA1 signing algorithm to create
     * signature for input using key.
     *
     * @param input - input, for which RSA-SHA1 signature is created
     * @param key   - key which is used for creation of RSA-SHA1 signature
     * @return - generated signature
     */
    byte[] sign(byte[] input, byte[] key);
}
