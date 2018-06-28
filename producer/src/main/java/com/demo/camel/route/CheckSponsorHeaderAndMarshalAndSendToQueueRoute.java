package com.demo.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
                    .log("Sending message to the queue '"+requestsToProcessQueueName+"': ${body}")
                    .setExchangePattern(ExchangePattern.InOnly)
                    .to("activemq:" + requestsToProcessQueueName)
                    .setBody(simple("Budgets request processed successfully!"))
                .otherwise()
                    .setBody(simple("HTTP request must contain the header '" + sponsorIdHeaderName + "'"))
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST))
            .endChoice();
        // @formatter:on
    }

}
