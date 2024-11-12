package com.panbet.manticore.oauth.headers.signature.rsa;


import com.google.common.base.Preconditions;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class RsaSha1SignatureGenerator implements SignatureGenerator {
    private final String privateKey;

    private final RsaSha1Signer signer;


    public RsaSha1SignatureGenerator(final String privateKey, final RsaSha1Signer signer) {
        Preconditions.checkArgument(privateKey != null, "privateKey must be not null");
        Preconditions.checkArgument(signer != null, "signer must be not null");

        this.privateKey = privateKey;
        this.signer = signer;
    }


    @Override
    public String getMethod() {
        return "RSA-SHA1";
    }


    @Override
    public String generateSignature(final String signatureBase) {
        final byte[] signatureBytes = signatureBase.getBytes(StandardCharsets.UTF_8);

        final byte[] key = Base64.getDecoder().decode(privateKey.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(signer.sign(signatureBytes, key));
    }
}
