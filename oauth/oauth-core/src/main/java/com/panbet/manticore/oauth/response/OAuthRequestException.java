package com.panbet.manticore.oauth.response;


import java.io.IOException;


/**
 * Signals that request execution has failed and no
 * response from server was obtained.
 */
public class OAuthRequestException extends IOException {
    public OAuthRequestException(final Throwable cause) {
        super(cause);
    }


    public OAuthRequestException(final Throwable cause, final String body) {
        super(cause);
    }
}
