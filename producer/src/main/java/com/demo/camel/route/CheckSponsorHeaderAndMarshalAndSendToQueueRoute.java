/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CheckSponsorHeaderAndMarshalAndSendToQueueRoute extends SpringRouteBuilder {

    public static final String ROUTE_ID = "enrichCarBudgetsRequest";
    public static final String ROUTE_URI = "direct:enrichCarBudgetRequests";

    @Value("${sponsorId.header.name}")
    private String sponsorIdHeaderName;

    @Value("${requestsToProcess.queue.name}")
    private String requestsToProcessQueueName;

    @Override
    public void configure() {
        // @formatter:off
        from(ROUTE_URI)
            .id(ROUTE_ID)
            .choice()
                .when(header(sponsorIdHeaderName).isNotNull())
                    .marshal()
                    .json(JsonLibrary.Jackson)
                    .to("activemq:" + requestsToProcessQueueName)
                    .setExchangePattern(ExchangePattern.InOnly)
                .otherwise()
                    .setBody(constant("HTTP request must contain the header '" + sponsorIdHeaderName + "'"))
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .endChoice();
        // @formatter:on
    }

}
