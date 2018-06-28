/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel.component;

import static com.demo.camel.component.ImgFinderConstants.RESULT_HEADER_NAME;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

public class ImgFinderConsumer extends ScheduledPollConsumer {

    private final ImgFinderEndpoint endpoint;

    public ImgFinderConsumer(final ImgFinderEndpoint endpoint, final Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    protected int poll() throws Exception {
        final Exchange exchange = endpoint.createExchange();
        final String queryResult = "https://www.coches.com/fotos_historicas/peugeot/5008-GT-2016/high_peugeot_5008-gt-2016_r7.jpg";
        exchange.getOut().setHeader(RESULT_HEADER_NAME, queryResult);
        exchange.getIn().setBody("An image url of a Peugeot 5008 was stored in the header 'imgfinderResult'!");

        try {
            // send message to next processor in the route
            getProcessor().process(exchange);
            return 1; // number of messages polled
        } finally {
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
        }
    }
}
