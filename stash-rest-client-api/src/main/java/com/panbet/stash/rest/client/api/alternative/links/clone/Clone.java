package com.panbet.stash.rest.client.api.alternative.links.clone;


import java.io.Serializable;
import java.net.URI;


public interface Clone extends Serializable {
    URI getHref();

    String getName();
}
