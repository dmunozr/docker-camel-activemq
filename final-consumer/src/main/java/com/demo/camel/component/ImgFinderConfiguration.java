package com.demo.camel.component;

import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public class ImgFinderConfiguration {

    @UriParam
    private String headerContainingQuery;

    @UriParam(defaultValue = "5000")
    private int delay;

    public String getHeaderContainingQuery() {
        return headerContainingQuery;
    }

    public void setHeaderContainingQuery(final String headerContainingQuery) {
        this.headerContainingQuery = headerContainingQuery;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(final int delay) {
        this.delay = delay;
    }
}
