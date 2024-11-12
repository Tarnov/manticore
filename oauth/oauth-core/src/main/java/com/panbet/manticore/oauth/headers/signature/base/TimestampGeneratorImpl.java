package com.panbet.manticore.oauth.headers.signature.base;


public class TimestampGeneratorImpl implements TimestampGenerator {
    @Override
    public long generateTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
