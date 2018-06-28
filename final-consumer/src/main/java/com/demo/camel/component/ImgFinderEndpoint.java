/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

@UriEndpoint(scheme = "imgfinder", syntax = "imgfinder", title = "Image finder by Yahoo search.")
public class ImgFinderEndpoint extends DefaultEndpoint {

    @UriParam
    private String headerContainingQuery;

    public ImgFinderEndpoint() {
    }

    public ImgFinderEndpoint(final String uri, final ImgFinderComponent component) {
        super(uri, component);
    }

    @Override
    public Producer createProducer() {
        return new ImgFinderProducer(this);
    }

    @Override
    public Consumer createConsumer(final Processor processor) {
        return new ImgFinderConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getHeaderContainingQuery() {
        return headerContainingQuery;
    }

    public void setHeaderContainingQuery(final String headerContainingQuery) {
        this.headerContainingQuery = headerContainingQuery;
    }
}
