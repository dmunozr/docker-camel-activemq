package com.demo.camel.component;

import static com.demo.camel.component.ImgFinderConstants.RESULT_HEADER_NAME;

import com.demo.util.WebSearchUtil;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.apache.commons.lang3.StringUtils;

public class ImgFinderConsumer extends ScheduledPollConsumer {

    private final ImgFinderEndpoint endpoint;

    public ImgFinderConsumer(final ImgFinderEndpoint endpoint, final Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        setDelay(endpoint.getConfiguration().getDelay());
    }

    @Override
    protected int poll() throws Exception {
        final Exchange exchange = endpoint.createExchange();
        final String headerContainingQuery = endpoint.getConfiguration().getHeaderContainingQuery();
        final String queryResult;
        if (StringUtils.isBlank(headerContainingQuery)) {
            queryResult = "https://www.coches.com/fotos_historicas/peugeot/5008-GT-2016/high_peugeot_5008-gt-2016_r7.jpg";
        } else {
            final String query = (String) exchange.getIn().getHeader(headerContainingQuery);
            queryResult = WebSearchUtil.searchImage(query);
        }
        exchange.getOut().setHeader(RESULT_HEADER_NAME, queryResult);
        exchange.getIn().setBody("An image url was stored in the header " + RESULT_HEADER_NAME);

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
