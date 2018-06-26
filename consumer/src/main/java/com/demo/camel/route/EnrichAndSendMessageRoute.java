/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel.route;

import com.demo.camel.handle.HandleCarBudgetRequest;
import com.demo.dto.CarBudgetRequest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnrichAndSendMessageRoute extends RouteBuilder {

    final JacksonDataFormat jsonDataFormat = new JacksonDataFormat(CarBudgetRequest.class);

    public static final String ROUTE_ID = "enrichCarBudgetRequest";
    public static final String ROUTE_URI = "direct:enrichCarRequest";

    @Value("${requestsProcessed.topic.name}")
    private String requestsProcessedTopicName;

    @Autowired
    private HandleCarBudgetRequest handleCarBudgetRequest;

    @Override
    public void configure() {
        from(ROUTE_URI)
            .id(ROUTE_ID)
            .unmarshal(jsonDataFormat)
            .bean(handleCarBudgetRequest)
            .to("activemq:topic:" + requestsProcessedTopicName + "?exchangePattern=InOnly");
    }

}
