package com.panbet.manticore.oauth.entities;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;


public class TokenImpl implements Token {
    private final String token;

    private final String tokenSecret;


    public TokenImpl(final String token, final String tokenSecret) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token), "token must be not empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(tokenSecret), "tokenSecret must be not empty");

        this.token = token;
        this.tokenSecret = tokenSecret;
    }


    @Override
    public String getToken() {
        return token;
    }


    @Override
    public String getTokenSecret() {
        return tokenSecret;
    }
}
