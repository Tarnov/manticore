package com.panbet.manticore.oauth.headers.signature.rsa;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RsaSha1SignatureGeneratorTest {
    private static final String PRIVATE_KEY = "privateKey";

    private static final String PRIVATE_KEY_MESSAGE = "privateKey must be not null";

    private static final String SIGNER_MESSAGE = "signer must be not null";

    private static final String METHOD_NAME = "RSA-SHA1";

    @Mock
    private RsaSha1Signer signer;


    @BeforeEach
    void initMocks() {
        signer = mock(RsaSha1Signer.class);
    }


    @Test
    @DisplayName("Test of signature generation.")
    void testSignatureGeneration() {
        final String base = "TestBase";
        final byte[] baseBytes = base.getBytes(StandardCharsets.UTF_8);
        final byte[] pkBytes = Base64.getDecoder().decode(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));

        when(signer.sign(baseBytes, pkBytes)).thenReturn(new byte[]{1});

        final SignatureGenerator signatureGenerator = new RsaSha1SignatureGenerator(PRIVATE_KEY, signer);
        final String signature = signatureGenerator.generateSignature(base);

        verify(signer, times(1)).sign(baseBytes, pkBytes);
        assertEquals("AQ==", signature);
    }


    @Test
    @DisplayName("Test for exception thrown by signer")
    void testSignatureGeneration1() {
        final String base = "TestBase";
        final byte[] baseBytes = base.getBytes(StandardCharsets.UTF_8);
        final byte[] pkBytes = Base64.getDecoder().decode(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));

        final String msg = "Test exception";
        when(signer.sign(any(), any())).thenThrow(new RuntimeException(msg));

        final SignatureGenerator signatureGenerator = new RsaSha1SignatureGenerator(PRIVATE_KEY, signer);
        final Throwable t = assertThrows(RuntimeException.class,
                () -> signatureGenerator.generateSignature(base));

        verify(signer, times(1)).sign(baseBytes, pkBytes);
        assertEquals(msg, t.getMessage());
    }


    @Test
    void testGetMethod() {
        final SignatureGenerator signatureGenerator = new RsaSha1SignatureGenerator(PRIVATE_KEY, signer);
        assertEquals(METHOD_NAME, signatureGenerator.getMethod());
    }


    @ParameterizedTest
    @MethodSource("paramsProvider")
    void testInvalidParams(final String privateKey, final RsaSha1Signer signer, final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class,
                () -> new RsaSha1SignatureGenerator(privateKey, signer));
        assertEquals(message, t.getMessage());
    }


    static Stream<Arguments> paramsProvider() {
        final RsaSha1Signer signer = Mockito.mock(RsaSha1Signer.class);

        return Stream.of(Arguments.of(null, signer, PRIVATE_KEY_MESSAGE),
                Arguments.of(PRIVATE_KEY, null, SIGNER_MESSAGE));
    }
}
