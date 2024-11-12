package com.panbet.manticore.oauth.headers.signature.rsa;


/**
 * Signature generator encapsulates signature algorithm
 * which is used to generate signatures for supplied
 * Signature Base Strings.
 */
public interface SignatureGenerator {
    /**
     * @return - signature method used by this generator for
     * signature generation
     */
    String getMethod();

    /**
     * Applies encapsulated signature algorithm to a supplied
     * Signature Base String.
     *
     * @param signatureBase - Signature Base String to which signature
     *                      algorithm is applied
     * @return - signature which was generated from signatureBase
     * by signature generator
     */
    String generateSignature(String signatureBase);
}
