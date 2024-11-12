package com.panbet.manticore.oauth.headers;


import com.panbet.manticore.oauth.headers.signature.base.NonceGenerator;
import com.panbet.manticore.oauth.headers.signature.base.SignatureBaseGenerator;
import com.panbet.manticore.oauth.headers.signature.base.TimestampGenerator;
import com.panbet.manticore.oauth.headers.signature.rsa.SignatureGenerator;
import com.panbet.manticore.oauth.headers.util.KeyValueAppender;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class HeaderGeneratorBuilderTest {
    private static final String APPENDER_MESSAGE = "appender must be not null";

    private static final String TIMESTAMP_MESSAGE = "timestampGenerator must be not null";

    private static final String NONCE_MESSAGE = "nonceGenerator must be not null";

    private static final String SIGNATURE_BASE_MESSAGE = "signatureBaseGenerator must be not null";

    private static final String SIGNATURE_MESSAGE = "signatureGenerator must be not null";


    @ParameterizedTest
    @MethodSource("paramsProvider")
    void testInvalidParams(final KeyValueAppender kvAppender, final TimestampGenerator timestampGenerator,
                           final NonceGenerator nonceGenerator, final SignatureBaseGenerator signatureBaseGenerator,
                           final SignatureGenerator signatureGenerator, final String message) {
        final Throwable t = assertThrows(IllegalArgumentException.class,
                () -> new HeaderGeneratorBuilder()
                        .setAppender(kvAppender)
                        .setTimestampGenerator(timestampGenerator)
                        .setNonceGenerator(nonceGenerator)
                        .setSignatureBaseGenerator(signatureBaseGenerator)
                        .setSignatureGenerator(signatureGenerator)
                        .build());
        assertEquals(message, t.getMessage());
    }


    private static Stream<Arguments> paramsProvider() {
        final KeyValueAppender kvAppender = Mockito.mock(KeyValueAppender.class);
        final TimestampGenerator timestampGenerator = Mockito.mock(TimestampGenerator.class);
        final NonceGenerator nonceGenerator = Mockito.mock(NonceGenerator.class);
        final SignatureBaseGenerator signatureBaseGenerator = Mockito.mock(SignatureBaseGenerator.class);
        final SignatureGenerator signatureGenerator = Mockito.mock(SignatureGenerator.class);

        return Stream.of(Arguments.of(null, timestampGenerator, nonceGenerator, signatureBaseGenerator,
                        signatureGenerator, APPENDER_MESSAGE),
                Arguments.of(kvAppender, null, nonceGenerator, signatureBaseGenerator, signatureGenerator,
                        TIMESTAMP_MESSAGE),
                Arguments.of(kvAppender, timestampGenerator, null, signatureBaseGenerator,
                        signatureGenerator, NONCE_MESSAGE),
                Arguments.of(kvAppender, timestampGenerator, nonceGenerator, null, signatureGenerator,
                        SIGNATURE_BASE_MESSAGE),
                Arguments.of(kvAppender, timestampGenerator, nonceGenerator, signatureBaseGenerator,
                        null, SIGNATURE_MESSAGE));
    }
}
