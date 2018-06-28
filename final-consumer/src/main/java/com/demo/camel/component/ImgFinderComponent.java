package com.demo.camel.component;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

public class ImgFinderComponent extends DefaultComponent {
    @Override
    protected Endpoint createEndpoint(final String uri, final String remaining, final Map<String, Object> parameters) throws Exception {
        final Endpoint endpoint = new ImgFinderEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }

}
