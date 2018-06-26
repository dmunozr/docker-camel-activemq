/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessCarBudgetsRequestsProcessedAndStoreThemInDiskRoute extends RouteBuilder {

    public static final String ROUTE_ID = "processCarBudgetsRequestsAndSendThemToTopic";

    @Value("${car.brand.header.name}")
    private String carBrandHeaderName;

    @Value("${requestsProcessed.topic.name}")
    private String requestsProcessedTopicName;

    @Override
    public void configure() {
        from("activemq:topic:" + requestsProcessedTopicName)
            .routeId(ROUTE_ID)
            .log("Final1: ${body}")
            .filter(header(carBrandHeaderName).isEqualTo("Peugeot"))
            .log("Final2: ${body}")
            .setExchangePattern(ExchangePattern.InOnly);
    }

}
