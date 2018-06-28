package com.demo.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessRequestsAndSendMessagesToTheTopicRoute extends RouteBuilder {

    public static final String ROUTE_ID = "processCarBudgetsRequestsAndSendThemToTopic";

    @Value("${requestsToProcess.queue.name}")
    private String requestsToProcessQueueName;

    @Override
    public void configure() {
        from("activemq:" + requestsToProcessQueueName)
            .routeId(ROUTE_ID)
            .split()
            .jsonpath("$.requests.*")
            .log("Processing the message: ${body}")
            .to(EnrichAndSendMessageRoute.ROUTE_URI);
    }

}
