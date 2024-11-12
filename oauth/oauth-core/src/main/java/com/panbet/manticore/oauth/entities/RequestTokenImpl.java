package com.panbet.manticore.oauth.entities;


import com.google.common.base.Preconditions;


public class RequestTokenImpl extends TokenImpl implements RequestToken {
    private final boolean callbackConfirmed;


    public RequestTokenImpl(final String token, final String tokenSecret, final Boolean callbackConfirmed) {
        super(token, tokenSecret);

        Preconditions.checkArgument(callbackConfirmed != null, "callbackConfirmed must be not null");
        this.callbackConfirmed = callbackConfirmed;
    }


    @Override
    public boolean callbackConfirmed() {
        return callbackConfirmed;
    }
}
