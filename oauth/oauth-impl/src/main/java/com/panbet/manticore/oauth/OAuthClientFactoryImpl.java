package com.panbet.manticore.oauth;


import com.panbet.http.request.executor.HttpRequestExecutor;
import com.panbet.http.request.executor.response.ResponseParser;
import com.panbet.manticore.oauth.auth.AuthHandlerFactory;
import com.panbet.manticore.oauth.auth.AuthHandlerFactoryImpl;
import com.panbet.manticore.oauth.entities.RequestToken;
import com.panbet.manticore.oauth.entities.Token;
import com.panbet.manticore.oauth.headers.HeaderGenerator;
import com.panbet.manticore.oauth.headers.HeaderGeneratorBuilder;
import com.panbet.manticore.oauth.headers.signature.base.NonceGeneratorImpl;
import com.panbet.manticore.oauth.headers.signature.base.SignatureBaseGeneratorImpl;
import com.panbet.manticore.oauth.headers.signature.base.TimestampGeneratorImpl;
import com.panbet.manticore.oauth.headers.signature.rsa.RsaSha1SignatureGenerator;
import com.panbet.manticore.oauth.headers.signature.rsa.RsaSha1SignerImpl;
import com.panbet.manticore.oauth.headers.util.Escaper;
import com.panbet.manticore.oauth.headers.util.GuavaPercentEscaper;
import com.panbet.manticore.oauth.headers.util.KeyValueAppender;
import com.panbet.manticore.oauth.headers.util.KeyValueAppenderImpl;
import com.panbet.manticore.oauth.parsers.AccessTokenResponseParser;
import com.panbet.manticore.oauth.parsers.RequestTokenResponseParser;

import java.security.SecureRandom;
import java.util.function.Supplier;


public class OAuthClientFactoryImpl implements OAuthClientFactory {
    private static final ResponseParser<RequestToken> REQUEST_TOKEN_RESPONSE_PARSER = new RequestTokenResponseParser();

    private static final ResponseParser<Token> ACCESS_TOKEN_RESPONSE_PARSER = new AccessTokenResponseParser();

    private final AuthHandlerFactory authHandlerFactory;

    private final Supplier<HttpRequestExecutor> executorWrapperSupplier;


    public OAuthClientFactoryImpl(final Supplier<HttpRequestExecutor> executorWrapperSupplier, final String privateKey,
                                  final SecureRandom secureRandom) {
        final Escaper escaper = new GuavaPercentEscaper();
        final KeyValueAppender appender = new KeyValueAppenderImpl(new GuavaPercentEscaper());

        final HeaderGenerator headerGenerator = new HeaderGeneratorBuilder()
                .setNonceGenerator(new NonceGeneratorImpl(secureRandom))
                .setTimestampGenerator(new TimestampGeneratorImpl())
                .setAppender(appender)
                .setSignatureBaseGenerator(new SignatureBaseGeneratorImpl(escaper, appender))
                .setSignatureGenerator(new RsaSha1SignatureGenerator(privateKey, new RsaSha1SignerImpl()))
                .build();
        this.authHandlerFactory = new AuthHandlerFactoryImpl(headerGenerator);
        this.executorWrapperSupplier = executorWrapperSupplier;
    }


    public OAuthClient createOAuthClient() {
        return new OAuthClientImplBuilder()
                .setAuthHandlerFactory(authHandlerFactory)
                .setHttpRequestExecutor(executorWrapperSupplier.get())
                .setRequestTokenParser(REQUEST_TOKEN_RESPONSE_PARSER)
                .setAccessTokenParser(ACCESS_TOKEN_RESPONSE_PARSER)
                .build();
    }
}
