package com.demo.camel.processor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.jayway.jsonpath.JsonPath;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class AddCarInformationAsHeaderProcessor implements Processor {

    public static final String QUERY_HEADER_NAME = "imgFinderQuery";

    private static final String QUERY_VALUE = "%s %s %s";

    @Override
    public void process(final Exchange exchange) throws IOException {
        final String json = IOUtils.toString((byte[]) exchange.getIn().getBody(), StandardCharsets.UTF_8.toString());
        final String brand = JsonPath.read(json, "$.car.brand");
        final String model = JsonPath.read(json, "$.car.model");
        final String version = JsonPath.read(json, "$.car.version");
        final String query = String.format(QUERY_VALUE, brand, model, version);

        exchange.getOut().setHeader(QUERY_HEADER_NAME, query);
        exchange.getOut().setBody(json);
    }
}
