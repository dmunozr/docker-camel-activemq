package com.demo.camel.processor;

import com.demo.storage.InMemoryMessageStorage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreMessagesProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(StoreMessagesProcessor.class);

    @Override
    public void process(final Exchange exchange) {

        InMemoryMessageStorage.INSTANCE.addMessage((JSONObject) exchange.getIn().getBody());
    }
}
