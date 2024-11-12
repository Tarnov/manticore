package com.panbet.manticore.oauth;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.HttpRequestExecutor;
import com.panbet.http.request.executor.RequestExecutorException;
import com.panbet.http.request.executor.ResponseHandlingException;
import com.panbet.http.request.executor.auth.AuthHandler;
import com.panbet.http.request.executor.headers.Header;
import com.panbet.http.request.executor.request.*;
import com.panbet.http.request.executor.response.EntityEnclosingResponseHandler;
import com.panbet.http.request.executor.response.ResponseParser;
import com.panbet.http.request.executor.response.ResponseStatus;
import com.panbet.http.request.executor.response.SimpleResponseHandler;
import com.panbet.manticore.oauth.auth.AuthHandlerFactory;
import com.panbet.manticore.oauth.entities.RequestToken;
import com.panbet.manticore.oauth.entities.Token;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;
import com.panbet.manticore.oauth.request.OAuthResponseHandler;
import com.panbet.manticore.oauth.request.ResponseHandlerWrapper;
import com.panbet.manticore.oauth.response.OAuthRequestException;
import com.panbet.manticore.oauth.response.OAuthResponseException;
import com.panbet.manticore.oauth.response.OAuthResponseStatus;
import com.panbet.manticore.oauth.response.OAuthResponseStatusImpl;

import java.util.Collection;


public class OAuthClientImpl implements OAuthClient {
    private final AuthHandlerFactory authHandlerFactory;

    private final HttpRequestExecutor requestExecutor;

    private final ResponseParser<RequestToken> requestTokenParser;

    private final ResponseParser<Token> accessTokenParser;


    OAuthClientImpl(final AuthHandlerFactory authHandlerFactory, final HttpRequestExecutor requestExecutor,
                    final ResponseParser<RequestToken> requestTokenParser, final ResponseParser<Token> accessTokenParser) {
        Preconditions.checkArgument(authHandlerFactory != null, "authHandlerFactory must be not null");
        Preconditions.checkArgument(requestExecutor != null, "requestExecutor must be not null");
        Preconditions.checkArgument(requestTokenParser != null, "requestTokenParser must be not null");
        Preconditions.checkArgument(accessTokenParser != null, "accessTokenParser must be not null");

        this.authHandlerFactory = authHandlerFactory;
        this.requestExecutor = requestExecutor;
        this.requestTokenParser = requestTokenParser;
        this.accessTokenParser = accessTokenParser;
    }


    @Override
    public RequestToken obtainRequestToken(final RequestTokenData requestTokenData) throws OAuthRequestException {
        final HttpMethod httpMethod = HttpMethod.valueOf(requestTokenData.getHttpMethod());
        final AuthHandler authHandler = authHandlerFactory.createRequestTokenAuthHandler(requestTokenData);
        final Collection<Header> authHeaders = authHandler.createAuthHeaders();

        final Request request = new RequestImpl(httpMethod, requestTokenData.getRequestUri(), authHeaders);

        final EntityEnclosingResponseHandler<RequestToken> responseHandler = new SimpleResponseHandler<>(requestTokenParser);

        return tryExecute(() -> requestExecutor.executeRequest(request, responseHandler));
    }


    @Override
    public Token obtainAccessToken(final AccessTokenData accessData) throws OAuthRequestException {
        final HttpMethod httpMethod = HttpMethod.valueOf(accessData.getHttpMethod());
        final AuthHandler authHandler = authHandlerFactory.createAccessTokenAuthHandler(accessData);
        final Collection<Header> authHeaders = authHandler.createAuthHeaders();

        final Request request = new RequestImpl(httpMethod, accessData.getRequestUri(), authHeaders);

        final EntityEnclosingResponseHandler<Token> responseHandler = new SimpleResponseHandler<>(accessTokenParser);

        return tryExecute(() -> requestExecutor.executeRequest(request, responseHandler));
    }


    @Override
    public <T> T executeRequest(final ResourceAccessData resourceAccessData,
                                final OAuthResponseHandler<T> responseHandler) throws OAuthRequestException {
        final HttpMethod httpMethod = HttpMethod.valueOf(resourceAccessData.getHttpMethod());
        final AuthHandler authHandler = authHandlerFactory.createRequestResourceAuthHandler(resourceAccessData);
        final Collection<Header> authHeaders = authHandler.createAuthHeaders();

        final Request request = new RequestImpl(httpMethod, resourceAccessData.getRequestUri(), authHeaders);

        final EntityEnclosingResponseHandler<T> wrappedResponseHandler = new ResponseHandlerWrapper<>(responseHandler);

        return tryExecute(() -> requestExecutor.executeRequest(request, wrappedResponseHandler));
    }


    @Override
    public <T> T executeRequest(final ResourceAccessData resourceAccessData, final String entity,
                                final OAuthResponseHandler<T> responseHandler) throws OAuthRequestException {
        final HttpMethod httpMethod = HttpMethod.valueOf(resourceAccessData.getHttpMethod());
        final AuthHandler authHandler = authHandlerFactory.createRequestResourceAuthHandler(resourceAccessData);
        final Collection<Header> authHeaders = authHandler.createAuthHeaders();

        final EntityEnclosingRequest request = new EntityEnclosingRequestImpl(httpMethod,
                resourceAccessData.getRequestUri(), authHeaders, entity);

        final EntityEnclosingResponseHandler<T> wrappedResponseHandler = new ResponseHandlerWrapper<>(responseHandler);

        return tryExecute(() -> requestExecutor.executeRequest(request, wrappedResponseHandler));
    }


    private <T> T tryExecute(final ThrowingSupplier<T> code) throws OAuthRequestException {
        try {
            return code.get();
        } catch (final ResponseHandlingException e) {
            final OAuthResponseStatus responseStatus = convertResponseStatus(e.getResponseStatus());

            throw new OAuthResponseException(e, responseStatus);
        } catch (final RequestExecutorException e) {
            throw new OAuthRequestException(e);
        }
    }


    private OAuthResponseStatus convertResponseStatus(final ResponseStatus responseStatus) {
        return new OAuthResponseStatusImpl(responseStatus.getStatusCode(), responseStatus.getReasonPhrase().orElse(null));
    }


    @Override
    public void close() throws Exception {
        requestExecutor.close();
    }


    @FunctionalInterface
    private interface ThrowingSupplier<T> {
        T get() throws RequestExecutorException;
    }
}
