package com.demo.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

@UriEndpoint(scheme = "imgfinder", syntax = "imgfinder://operationPath", title = "Image finder by Yahoo search.")
public class ImgFinderEndpoint extends DefaultEndpoint {

    private String operationPath;

    @UriParam
    private ImgFinderConfiguration configuration;

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

    public String getOperationPath() {
        return operationPath;
    }

    public void setOperationPath(final String operationPath) {
        this.operationPath = operationPath;
    }

    public ImgFinderConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(final ImgFinderConfiguration configuration) {
        this.configuration = configuration;
    }
}
