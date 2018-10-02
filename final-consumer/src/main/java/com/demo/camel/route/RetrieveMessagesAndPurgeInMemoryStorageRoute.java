package com.demo.camel.route;

import com.demo.storage.InMemoryMessageStorage;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RetrieveMessagesAndPurgeInMemoryStorageRoute extends SpringRouteBuilder {

    public static final String ROUTE_ID = "retrieveMessagesAndPurgeInMemoryStorage";
    public static final String ROUTE_URI = "direct:retrieveMessagesAndPurgeInMemoryStorage";

    @Override
    public void configure() {
        // @formatter:off
        from(ROUTE_URI)
            .id(ROUTE_ID)
            .setBody(method(InMemoryMessageStorage.INSTANCE,"getAllMessages"));
        // @formatter:on
    }

}
