package com.panbet.manticore.oauth.headers.signature.rsa;


import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.RSADigestSigner;
import org.bouncycastle.crypto.util.PrivateKeyFactory;

import java.io.IOException;


/**
 * {@inheritDoc}
 * <p>
 * This implementation uses bouncycastle library to
 * implement RSA-SHA1 signature generation.
 * <p>
 * See <a href="http://www.bouncycastle.org/">http://www.bouncycastle.org/</a>
 * for further information about library.
 */
public class RsaSha1SignerImpl implements RsaSha1Signer {
    @Override
    public byte[] sign(final byte[] input, final byte[] key) {
        try {
            final RSADigestSigner rsaDigestSigner = new RSADigestSigner(new SHA1Digest());
            rsaDigestSigner.init(true, getRSAKey(key));

            rsaDigestSigner.update(input, 0, input.length);

            return rsaDigestSigner.generateSignature();
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to generate signature. ", e);
        }
    }


    private RSAKeyParameters getRSAKey(final byte[] key) throws IOException {
        return (RSAKeyParameters) PrivateKeyFactory.createKey(key);
    }
}
