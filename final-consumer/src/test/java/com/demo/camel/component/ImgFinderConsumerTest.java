package com.demo.camel.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImgFinderConsumerTest {

    private static final Logger logger = LoggerFactory.getLogger(ImgFinderConsumer.class);

    public static void main(final String[] args) throws Exception {
        logger.info("Starting Custom Apache Camel component example");
        logger.info("Press CTRL+C to terminate the JVM");

        final Main main = new Main();
        main.addRouteBuilder(new RouteBuilder() {

            @Override
            public void configure() {
                from("imgfinder:search?headerContainingQuery=imgFinderQuery&delay=5000")
                    .split(body())
                    .log(body().toString());
            }
        });
        main.run();
    }
}
