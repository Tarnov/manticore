package com.panbet.manticore.oauth.parsers;


import com.panbet.manticore.oauth.entities.RequestToken;
import com.panbet.manticore.oauth.entities.RequestTokenImpl;

import java.util.Map;


public class RequestTokenResponseParser extends TokenResponseParser<RequestToken> {
    @Override
    protected RequestToken parseAdditionalAndReturn(final String token, final String tokenSecret,
                                                    final Map<String, String> splittedKeyValues) {
        final String callbackConfirmed = splittedKeyValues.get("oauth_callback_confirmed");
        if (callbackConfirmed == null) {
            throw new IllegalArgumentException("Parameter 'oauth_callback_confirmed' is missing.");
        }

        return new RequestTokenImpl(token, tokenSecret, Boolean.parseBoolean(callbackConfirmed));
    }
}
