package com.demo.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoCamelConfig {

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(final CamelContext context) {
                // Nothing to do here
            }

            @Override
            public void afterApplicationStart(final CamelContext context) {
                // Nothing to do here
            }
        };
    }

}
