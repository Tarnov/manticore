package com.panbet.manticore.oauth;


import com.panbet.manticore.oauth.entities.RequestToken;
import com.panbet.manticore.oauth.entities.Token;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;
import com.panbet.manticore.oauth.request.OAuthResponseHandler;
import com.panbet.manticore.oauth.response.OAuthRequestException;


/**
 * Used to execute requests using OAuth 1.0a for
 * authorization.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface OAuthClient extends AutoCloseable {
    /**
     * Obtains an Unauthorized Request Token.
     *
     * @param requestData - request parameters.
     * @return - Unauthorized Request Token.
     * @throws OAuthRequestException - if request wasn't performed or if response handling failed
     */
    RequestToken obtainRequestToken(RequestTokenData requestData) throws OAuthRequestException;

    /**
     * Obtains an Access Token.
     *
     * @param accessTokenData - request parameters.
     * @return - Access Token.
     * @throws OAuthRequestException - if request wasn't performed or if response handling failed
     */
    Token obtainAccessToken(AccessTokenData accessTokenData) throws OAuthRequestException;

    /**
     * This method executes request WITHOUT body, using
     * OAuth 1.0a for authorization handling.
     *
     * @param resourceAccessData - request parameters.
     * @param responseHandler    - response handler which maps server response
     *                           to java object.
     * @param <T>                - type of java object to which server response is mapped.
     * @return - object created from server response by response handler.
     * @throws OAuthRequestException - if request wasn't performed or if response handling failed.
     */
    <T> T executeRequest(ResourceAccessData resourceAccessData, OAuthResponseHandler<T> responseHandler)
            throws OAuthRequestException;

    /**
     * This method executes request WITH body, using
     * OAuth 1.0a for authorization handling.
     *
     * @param resourceAccessData - request parameters.
     * @param entity             - request body.
     * @param responseHandler    - response handler which maps server response
     *                           to java object.
     * @param <T>                - type of java object to which server response is mapped.
     * @return - object created from server response by response handler.
     * @throws OAuthRequestException - if request wasn't performed or if response handling failed.
     */
    <T> T executeRequest(ResourceAccessData resourceAccessData, String entity, OAuthResponseHandler<T> responseHandler)
            throws OAuthRequestException;
}
