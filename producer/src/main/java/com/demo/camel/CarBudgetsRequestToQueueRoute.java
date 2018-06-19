/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.camel;

import com.demo.dto.CarBudgetsRequest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class CarBudgetsRequestToQueueRoute extends RouteBuilder {

    @Override
    public void configure() {
        rest("/v1/requests")
            .post()
            .type(CarBudgetsRequest.class)
            .consumes(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .to("log:com.mycompany.order?level=DEBUG");
    }

}
