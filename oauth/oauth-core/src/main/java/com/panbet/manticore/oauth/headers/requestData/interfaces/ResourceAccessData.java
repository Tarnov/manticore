package com.panbet.manticore.oauth.headers.requestData.interfaces;


/**
 * Describes attributes specific for requests used to
 * access Protected Resources.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface ResourceAccessData extends OAuthRequestData {
    String getAccessToken();
}
