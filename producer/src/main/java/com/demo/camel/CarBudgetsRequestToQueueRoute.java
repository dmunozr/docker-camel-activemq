/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel;

import com.demo.dto.CarBudgetsRequest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class CarBudgetsRequestToQueueRoute extends RouteBuilder {

    @Value("requestsToProcess.queue.name")
    private String requestsToProcessQueueName;

    @Override
    public void configure() {
        rest("/v1/requests")
            .post()
            .bindingMode(RestBindingMode.json)
            .type(CarBudgetsRequest.class)
            .consumes(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .to("jms:" + requestsToProcessQueueName + "?exchangePattern=InOnly");
    }

}
