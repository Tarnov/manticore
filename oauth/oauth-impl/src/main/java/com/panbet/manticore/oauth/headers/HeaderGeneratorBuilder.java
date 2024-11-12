package com.panbet.manticore.oauth.headers;


import com.panbet.manticore.oauth.headers.signature.base.NonceGenerator;
import com.panbet.manticore.oauth.headers.signature.base.SignatureBaseGenerator;
import com.panbet.manticore.oauth.headers.signature.base.TimestampGenerator;
import com.panbet.manticore.oauth.headers.signature.rsa.SignatureGenerator;
import com.panbet.manticore.oauth.headers.util.KeyValueAppender;


public class HeaderGeneratorBuilder {
    private KeyValueAppender appender;

    private TimestampGenerator timestampGenerator;

    private NonceGenerator nonceGenerator;

    private SignatureBaseGenerator signatureBaseGenerator;

    private SignatureGenerator signatureGenerator;


    public HeaderGeneratorBuilder setAppender(final KeyValueAppender appender) {
        this.appender = appender;
        return this;
    }


    public HeaderGeneratorBuilder setTimestampGenerator(final TimestampGenerator timestampGenerator) {
        this.timestampGenerator = timestampGenerator;
        return this;
    }


    public HeaderGeneratorBuilder setNonceGenerator(final NonceGenerator nonceGenerator) {
        this.nonceGenerator = nonceGenerator;
        return this;
    }


    public HeaderGeneratorBuilder setSignatureBaseGenerator(final SignatureBaseGenerator signatureBaseGenerator) {
        this.signatureBaseGenerator = signatureBaseGenerator;
        return this;
    }


    public HeaderGeneratorBuilder setSignatureGenerator(final SignatureGenerator signatureGenerator) {
        this.signatureGenerator = signatureGenerator;
        return this;
    }


    public HeaderGenerator build() {
        return new HeaderGeneratorImpl(appender, timestampGenerator, nonceGenerator, signatureBaseGenerator,
                signatureGenerator);
    }
}
