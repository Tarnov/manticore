package com.panbet.confluence.rest.client.api.internal;


import com.panbet.confluence.rest.client.api.domain.ContentRestClient;

import java.io.Closeable;
import java.io.IOException;


public interface ConfluenceRestClient extends Closeable {
    ContentRestClient getContentClient();


    void close() throws IOException;
}
