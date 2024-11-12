package com.panbet.manticore.oauth.parsers;


import com.panbet.http.request.executor.response.ResponseParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public abstract class TokenResponseParser<T> implements ResponseParser<T> {
    @Override
    public T parseResponse(final InputStream responseStream) {
        try (final Scanner s = new Scanner(responseStream).useDelimiter("[&]")) {
            final List<String> keyValuePairs = new ArrayList<>(3);
            while (s.hasNext()) {
                keyValuePairs.add(s.next());
            }

            return pairsToToken(keyValuePairs);
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to parse response. ", e);
        }
    }


    private T pairsToToken(final List<String> kVPairs) {
        final Map<String, String> splittedKeyValues = kVPairs.stream()
                .map(this::splitPair)
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

        final String token = splittedKeyValues.get("oauth_token");
        final String tokenSecret = splittedKeyValues.get("oauth_token_secret");

        if (token == null) {
            throw new IllegalArgumentException("Parameter 'oauth_token' is missing.");
        }

        if (tokenSecret == null) {
            throw new IllegalArgumentException("Parameter 'oauth_token_secret' is missing.");
        }

        return parseAdditionalAndReturn(token, tokenSecret, splittedKeyValues);
    }


    private String[] splitPair(final String pairAsString) {
        final String[] pair = pairAsString.split("=", 2);
        if (pair.length != 2) {
            throw new IllegalArgumentException("No value found for parameter " + pair[0]);
        }

        return pair;
    }


    protected abstract T parseAdditionalAndReturn(final String token, final String tokenSecret,
                                                  final Map<String, String> splittedKeyValues);
}
