package com.panbet.manticore.oauth.headers.util;


import com.google.common.net.PercentEscaper;


public class GuavaPercentEscaper implements Escaper {
    private static final PercentEscaper escaper = new PercentEscaper("-._~", false);


    @Override
    public String escape(final String s) {
        return escaper.escape(s);
    }
}
