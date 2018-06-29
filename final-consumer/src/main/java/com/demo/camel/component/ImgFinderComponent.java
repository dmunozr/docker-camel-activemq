package com.demo.camel.component;

import static com.demo.camel.component.ImgFinderConstants.SEARCH_ACTION_NAME;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

public class ImgFinderComponent extends DefaultComponent {
    @Override
    protected Endpoint createEndpoint(final String uri, final String remaining, final Map<String, Object> parameters) throws Exception {
        if (SEARCH_ACTION_NAME.equals(remaining)) {
            final Endpoint endpoint = new ImgFinderEndpoint(uri, this);
            final ImgFinderConfiguration configuration = new ImgFinderConfiguration();
            setProperties(configuration, parameters);
            ((ImgFinderEndpoint) endpoint).setConfiguration(configuration);
            return endpoint;
        } else {
            throw new IllegalArgumentException("Non-allowed operation path value: " + remaining);
        }
    }

}
