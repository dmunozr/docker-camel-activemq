package com.demo.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class PublishProcessedMessagesRoute extends RouteBuilder {

    @Override
    public void configure() {
        rest("/v1/messages")
            .get()
            .produces(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .to(RetrieveMessagesAndPurgeInMemoryStorageRoute.ROUTE_URI);
    }

}
