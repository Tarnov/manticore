package com.panbet.manticore.oauth.response;


/**
 * Signals that request was executed and response from server
 * was obtained, but response handling has failed.
 */
public class OAuthResponseException extends OAuthRequestException {
    private final OAuthResponseStatus responseStatus;


    public OAuthResponseException(final Throwable cause, final OAuthResponseStatus responseStatus) {
        super(cause);
        this.responseStatus = responseStatus;
    }


    public OAuthResponseException(final Throwable cause, final String body, final OAuthResponseStatus responseStatus) {
        super(cause, body);
        this.responseStatus = responseStatus;
    }


    /**
     * @return - http response status returned by server
     */
    public OAuthResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
