package com.panbet.manticore.oauth.headers;


import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.methodParameters.MethodParameter;
import com.panbet.manticore.oauth.headers.methodParameters.SimpleMethodParameter;
import com.panbet.manticore.oauth.headers.methodParameters.StandardParameterNames;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.OAuthRequestData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;
import com.panbet.manticore.oauth.headers.signature.base.NonceGenerator;
import com.panbet.manticore.oauth.headers.signature.base.SignatureBaseGenerator;
import com.panbet.manticore.oauth.headers.signature.base.TimestampGenerator;
import com.panbet.manticore.oauth.headers.signature.rsa.SignatureGenerator;
import com.panbet.manticore.oauth.headers.util.KeyValueAppender;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class HeaderGeneratorImpl implements HeaderGenerator {
    private static final String HEADER_START = "OAuth ";

    private static final char HEADER_PARAMETERS_SEPARATOR = ',';

    private final KeyValueAppender appender;

    private final TimestampGenerator timestampGenerator;

    private final NonceGenerator nonceGenerator;

    private final SignatureBaseGenerator signatureBaseGenerator;

    private final SignatureGenerator signatureGenerator;


    HeaderGeneratorImpl(final KeyValueAppender appender, final TimestampGenerator timestampGenerator,
                        final NonceGenerator nonceGenerator, final SignatureBaseGenerator signatureBaseGenerator,
                        final SignatureGenerator signatureGenerator) {
        Preconditions.checkArgument(appender != null, "appender must be not null");
        Preconditions.checkArgument(timestampGenerator != null, "timestampGenerator must be not null");
        Preconditions.checkArgument(nonceGenerator != null, "nonceGenerator must be not null");
        Preconditions.checkArgument(signatureBaseGenerator != null, "signatureBaseGenerator must be not null");
        Preconditions.checkArgument(signatureGenerator != null, "signatureGenerator must be not null");

        this.appender = appender;
        this.timestampGenerator = timestampGenerator;
        this.nonceGenerator = nonceGenerator;
        this.signatureBaseGenerator = signatureBaseGenerator;
        this.signatureGenerator = signatureGenerator;
    }


    @Override
    public String generateRequestTokenHeader(final RequestTokenData data) {
        return generateHeader(data, parameters ->
                parameters.add(new SimpleMethodParameter(StandardParameterNames.CALLBACK, data.getCallback())));
    }


    @Override
    public String generateAccessTokenHeader(final AccessTokenData data) {
        return generateHeader(data, parameters ->
        {
            parameters.add(new SimpleMethodParameter(StandardParameterNames.TOKEN, data.getRequestToken()));
            parameters.add(new SimpleMethodParameter(StandardParameterNames.VERIFICATION_CODE, data.getVerificationCode()));
        });
    }


    @Override
    public String generateRequestResourceHeader(final ResourceAccessData data) {
        return generateHeader(data, parameters ->
                parameters.add(new SimpleMethodParameter(StandardParameterNames.TOKEN, data.getAccessToken())));
    }


    private String generateHeader(final OAuthRequestData data, final Consumer<List<MethodParameter>> customParamAdder) {
        final List<MethodParameter> parameters = createCommonParameters(data);

        customParamAdder.accept(parameters);

        final String signature = computeSignature(data.getHttpMethod(), data.getRequestUri(), parameters);
        parameters.add(new SimpleMethodParameter(StandardParameterNames.SIGNATURE, signature));

        return createAuthenticationHeader(parameters);
    }


    private List<MethodParameter> createCommonParameters(final OAuthRequestData data) {
        final List<MethodParameter> parameters = new ArrayList<>();

        parameters.add(new SimpleMethodParameter(StandardParameterNames.CONSUMER_KEY, data.getConsumerKey()));
        parameters.add(new SimpleMethodParameter(StandardParameterNames.NONCE, nonceGenerator.generateNonce()));
        parameters.add(new SimpleMethodParameter(StandardParameterNames.SIGNATURE_METHOD, signatureGenerator.getMethod()));

        final String timestamp = Long.toString(timestampGenerator.generateTimestamp());
        parameters.add(new SimpleMethodParameter(StandardParameterNames.TIMESTAMP, timestamp));

        return parameters;
    }


    private String computeSignature(final String httpMethod, final URI requestUri,
                                    final List<MethodParameter> parameters) {
        final String signatureBase = signatureBaseGenerator.generateSignatureBase(httpMethod, requestUri, parameters);

        return signatureGenerator.generateSignature(signatureBase);
    }


    private String createAuthenticationHeader(final List<MethodParameter> parameters) {
        final List<String> joinedParameters = parameters.stream()
                .map(appender::appendForHeader)
                .sorted()
                .collect(Collectors.toList());

        return HEADER_START + Joiner.on(HEADER_PARAMETERS_SEPARATOR).join(joinedParameters);
    }
}
