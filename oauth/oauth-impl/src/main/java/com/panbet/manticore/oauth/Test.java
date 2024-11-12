package com.panbet.manticore.oauth;


import com.panbet.http.request.executor.HttpRequestExecutorImpl;
import com.panbet.http.request.executor.request.DefaultRequestPreparator;
import com.panbet.http.request.executor.request.RequestPreparator;
import com.panbet.http.request.executor.response.ResponseParser;
import com.panbet.manticore.oauth.auth.AuthHandlerFactoryImpl;
import com.panbet.manticore.oauth.entities.RequestToken;
import com.panbet.manticore.oauth.entities.Token;
import com.panbet.manticore.oauth.headers.HeaderGenerator;
import com.panbet.manticore.oauth.headers.HeaderGeneratorBuilder;
import com.panbet.manticore.oauth.headers.requestData.impl.OAuthRequestDataFactoryImpl;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.OAuthRequestDataFactory;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;
import com.panbet.manticore.oauth.headers.signature.base.NonceGeneratorImpl;
import com.panbet.manticore.oauth.headers.signature.base.SignatureBaseGenerator;
import com.panbet.manticore.oauth.headers.signature.base.SignatureBaseGeneratorImpl;
import com.panbet.manticore.oauth.headers.signature.base.TimestampGeneratorImpl;
import com.panbet.manticore.oauth.headers.signature.rsa.RsaSha1SignatureGenerator;
import com.panbet.manticore.oauth.headers.signature.rsa.RsaSha1Signer;
import com.panbet.manticore.oauth.headers.signature.rsa.RsaSha1SignerImpl;
import com.panbet.manticore.oauth.headers.util.Escaper;
import com.panbet.manticore.oauth.headers.util.GuavaPercentEscaper;
import com.panbet.manticore.oauth.headers.util.KeyValueAppenderImpl;
import com.panbet.manticore.oauth.parsers.AccessTokenResponseParser;
import com.panbet.manticore.oauth.parsers.RequestTokenResponseParser;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.security.SecureRandom;
import java.util.Scanner;


public class Test {
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

    private static final String BASE = "http://jira-test.cur.local:8080/";

    private static final OAuthRequestDataFactory oAuthRequestDataFactory = new OAuthRequestDataFactoryImpl("OauthKey");


    public static void main(String[] args) throws Exception {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        final RequestPreparator defaultRequestPreparator = new DefaultRequestPreparator();
        final HeaderGenerator headerGenerator = createHeaderGenerator();

        final ResponseParser<RequestToken> requestTokenParser = new RequestTokenResponseParser();
        final ResponseParser<Token> accessTokenParser = new AccessTokenResponseParser();

        final OAuthClient oAuthClient = new OAuthClientImplBuilder()
                .setAuthHandlerFactory(new AuthHandlerFactoryImpl(headerGenerator))
                .setHttpRequestExecutor(new HttpRequestExecutorImpl(httpClient, defaultRequestPreparator))
                .setRequestTokenParser(requestTokenParser)
                .setAccessTokenParser(accessTokenParser)
                .build();

        final String requestToken = requestToken(oAuthClient).getToken();
        Scanner scan = new Scanner(System.in);
        final String verificationCode = scan.nextLine();


        final String accessToken = accessToken(oAuthClient, requestToken, verificationCode).getToken();
        scan.nextLine();

        executeRequest(oAuthClient, accessToken);
    }


    private static RequestToken requestToken(final OAuthClient oAuthClient) throws Exception {
        final RequestTokenData requestTokenData = oAuthRequestDataFactory.createRequestTokenData("POST",
                new URI(BASE + "plugins/servlet/oauth/request-token"));

        final RequestToken requestToken = oAuthClient.obtainRequestToken(requestTokenData);
        System.out.println("rT = " + requestToken.getToken());
        System.out.println("tS = " + requestToken.getTokenSecret());
        System.out.println("cC = " + requestToken.callbackConfirmed());
        System.out.println();
        System.out.println("url = " + BASE +
                "plugins/servlet/oauth/authorize?oauth_token=" + requestToken.getToken());

        return requestToken;
    }


    private static Token accessToken(final OAuthClient oAuthClient, final String requestToken,
                                     final String verificationCode) throws Exception {
        final AccessTokenData accessTokenData = oAuthRequestDataFactory.createAccessTokenData("POST",
                new URI(BASE + "plugins/servlet/oauth/access-token"), requestToken, verificationCode);

        final Token accessToken = oAuthClient.obtainAccessToken(accessTokenData);

        System.out.println("aT = " + accessToken.getToken());
        System.out.println("tS = " + accessToken.getTokenSecret());

        return accessToken;
    }


    private static void executeRequest(final OAuthClient oAuthClient, final String accessToken) throws Exception {
        final ResourceAccessData resourceAccessData = oAuthRequestDataFactory.createResourceAccessData("GET",
                new URI(BASE + "rest/api/2/myself?expand=groups"), accessToken);

        final String s = oAuthClient.executeRequest(resourceAccessData, oAuthResponseData -> {
            try (Scanner s1 = new Scanner(oAuthResponseData.getEntityContent()).useDelimiter("\\A")) {
                return s1.hasNext() ? s1.next() : "";
            }
        });

        System.out.println();
        System.out.println("result = " + s);
    }


    private static HeaderGenerator createHeaderGenerator() {
        final RsaSha1Signer signer = new RsaSha1SignerImpl();
        final Escaper escaper = new GuavaPercentEscaper();
        final SignatureBaseGenerator signatureBaseGenerator = new SignatureBaseGeneratorImpl(escaper,
                new KeyValueAppenderImpl(escaper));

        return new HeaderGeneratorBuilder()
                .setAppender(new KeyValueAppenderImpl(escaper))
                .setTimestampGenerator(new TimestampGeneratorImpl())
                .setNonceGenerator(new NonceGeneratorImpl(new SecureRandom()))
                .setSignatureBaseGenerator(signatureBaseGenerator)
                .setSignatureGenerator(new RsaSha1SignatureGenerator(PRIVATE_KEY, signer))
                .build();
    }
}
