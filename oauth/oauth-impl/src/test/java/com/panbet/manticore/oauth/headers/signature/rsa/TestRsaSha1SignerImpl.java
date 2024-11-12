package com.panbet.manticore.oauth.headers.signature.rsa;


import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class TestRsaSha1SignerImpl {
    private static final String PRIVATE_KEY = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAPPGaggjaU9dzeVe" +
            "YW0o4NZGUJxwYxfAbS3iC05aA/KNoJ+CphbOlaQ9I02G/Fftd6ePqoNZEWkUvLL8" +
            "kYwD5/A5muuB+9qDOEHMwuP6Dcb0LjtDxV/5CQZr5AzPoxjCbDIdwWL/5ejjkOPQ" +
            "i+ZN8JkFKhPLBlFpNWRUpL9GdpbRAgMBAAECgYEAoVQyX+hvjWLbHGz1tTRUd/pY" +
            "5u9J5TIoDzGFofjYm/E219my6W0Wg3ciofAvmFFD2tStV/feGdjreNjFyg/bskj/" +
            "Raa0OiZoWsqtRA5jzY22LxX35bBofNMNVQyk7yDiQGuIrCaanwHLOIFb4fIFxHpt" +
            "3CQZtmMlm+eshCx9BUECQQD87wp3ng8mLm6P0K8TckBxlitT9A6+0bA2vRmVGMiF" +
            "E9c1+9WH0uB+8eylzxoy3xzNAbd4BK+fe4aWZJ1vNqltAkEA9rrzNVMwR4PhVrpC" +
            "VjYfQYb20K61HkXli3rn1k4uTT/7cNfaHENyX+LIWHGOqXvVCbQdaprqQ8O9mlBm" +
            "cdbIdQJBAKPhwhd0+v3TMeHO5dEkwuqV0ScJSBCNTq544b2AHFRVYajhrh3eHYVC" +
            "/QEF6kSx2Thfd1+1MZGU+MQzFuq7MRUCQQC0YGh9i5u4LMoIxZLJxDeFiE3YsmDn" +
            "COKP3gKvwehHwYbpMGTcVNLBFuKxBhPuBAHzXiJDVY3+jJOIxU6f2w4hAkEA1eI0" +
            "+Y9f5E3IGQi5bsRciQfvowy3RZ50cfBA24GjxqqpyquR7kA0waSXWBENlSMvqhl4" +
            "dOuRkqwV4g0caXel7A==";


    @Test
    void testSignatureGeneration() throws Exception {
        final byte[] key = Base64.getDecoder().decode(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
        final RsaSha1Signer rsaSha1Signer = new RsaSha1SignerImpl();

        final ProvidedRSASigner providedRSASigner = new ProvidedRSASigner(PRIVATE_KEY);

        final List<String> plainDatas = Arrays.asList("", "Hello World!",
                "Big big big string is coming! Hello World! Hello World! Hello World! Hello World! Hello World!");

        for (final String data : plainDatas) {
            final byte[] input = data.getBytes(StandardCharsets.UTF_8);

            final byte[] implResult = rsaSha1Signer.sign(input, key);
            final byte[] providedResult = providedRSASigner.sign(input);

            assertArrayEquals(implResult, providedResult);
        }
    }
}
