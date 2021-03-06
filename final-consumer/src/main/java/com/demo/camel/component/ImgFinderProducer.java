package com.demo.camel.component;

import static com.demo.camel.component.ImgFinderConstants.RESULT_HEADER_NAME;

import java.io.IOException;

import com.demo.util.WebSearchUtil;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

public class ImgFinderProducer extends DefaultProducer {

    private final ImgFinderEndpoint endpoint;

    public ImgFinderProducer(final ImgFinderEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void process(final Exchange exchange) throws IOException, InterruptedException {
        final String headerName = endpoint.getConfiguration().getHeaderContainingQuery();
        final String query = (String) exchange.getIn().getHeader(headerName);
        final String result = WebSearchUtil.searchImage(query);
        exchange.getOut().setHeader(RESULT_HEADER_NAME, result);
        exchange.getOut().setBody(exchange.getIn().getBody());
    }
}
