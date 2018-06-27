/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel.route;

import com.demo.filter.CarBrandFilter;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessCarBudgetsRequestsProcessedAndStoreThemInDiskRoute extends RouteBuilder {

    public static final String ROUTE_ID = "processCarBudgetsRequestsAndSendThemToTopic";

    @Value("${requestsProcessed.topic.name}")
    private String requestsProcessedTopicName;

    @Value("${container.name}")
    private String containerName;

    @Autowired
    private CarBrandFilter carBrandFilter;

    @Override
    public void configure() {
        from("activemq:topic:" + requestsProcessedTopicName)
            .routeId(ROUTE_ID)
            .filter().method(carBrandFilter, "filter")
            .log("Processed by " + containerName + ": ${body}");
    }

}
