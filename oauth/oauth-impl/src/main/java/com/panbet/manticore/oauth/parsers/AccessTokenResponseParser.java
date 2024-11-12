package com.panbet.manticore.oauth.parsers;


import com.panbet.manticore.oauth.entities.Token;
import com.panbet.manticore.oauth.entities.TokenImpl;

import java.util.Map;


public class AccessTokenResponseParser extends TokenResponseParser<Token> {
    @Override
    protected Token parseAdditionalAndReturn(final String token, final String tokenSecret,
                                             final Map<String, String> splittedKeyValues) {
        return new TokenImpl(token, tokenSecret);
    }
}
