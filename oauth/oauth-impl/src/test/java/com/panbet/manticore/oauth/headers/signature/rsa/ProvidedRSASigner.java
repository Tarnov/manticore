package com.panbet.manticore.oauth.headers.signature.rsa;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class ProvidedRSASigner {
    private final PrivateKey privateKey;


    public ProvidedRSASigner(final String privateKey) throws Exception {
        this.privateKey = getPrivateKey(privateKey);
    }


    public byte[] sign(final byte[] input) {
        try {
            final Signature signer = Signature.getInstance("SHA1withRSA", new BouncyCastleProvider());
            signer.initSign(privateKey);
            signer.update(input);

            return signer.sign();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to compute signature. ", e);
        }
    }


    private static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        byte[] privateBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }
}
