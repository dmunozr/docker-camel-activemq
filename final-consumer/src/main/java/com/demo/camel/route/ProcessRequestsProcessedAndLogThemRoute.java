package com.demo.camel.route;

import static com.demo.camel.processor.AddCarInformationAsHeaderProcessor.QUERY_HEADER_NAME;

import com.demo.camel.filter.CarBrandFilter;
import com.demo.camel.processor.AddCarInformationAsHeaderProcessor;
import com.demo.camel.processor.InjectImageUrlProcessor;
import com.demo.camel.processor.StoreMessagesProcessor;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessRequestsProcessedAndLogThemRoute extends RouteBuilder {

    public static final String ROUTE_ID = "processRequestsProcessedAndLogThemRoute";

    @Value("${requestsProcessed.topic.name}")
    private String requestsProcessedTopicName;

    @Value("${container.name}")
    private String containerName;

    @Autowired
    private CarBrandFilter carBrandFilter;

    @Autowired
    private AddCarInformationAsHeaderProcessor addCarInformationAsHeaderProcessor;

    @Autowired
    private InjectImageUrlProcessor injectImageUrlProcessor;

    @Autowired
    private StoreMessagesProcessor storeMessagesProcessor;

    @Override
    public void configure() {
        from("activemq:topic:" + requestsProcessedTopicName)
            .routeId(ROUTE_ID)
            .filter().method(carBrandFilter, "filter")
            .process(addCarInformationAsHeaderProcessor)
            .to("imgfinder:search?headerContainingQuery=" + QUERY_HEADER_NAME)
            .process(injectImageUrlProcessor)
            .process(storeMessagesProcessor)
            .log("Request processed successfully by " + containerName + ": ${body}");
    }
}
