package com.demo.camel;

import com.demo.camel.converter.JSONToCarBudgetRequestTypeConverter;

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
                configureTypeConverters(context);
            }

            @Override
            public void afterApplicationStart(final CamelContext context) {
                // Nothing to do here
            }
        };
    }

    private void configureTypeConverters(final CamelContext context) {
        final JSONToCarBudgetRequestTypeConverter myTypeConverters = new JSONToCarBudgetRequestTypeConverter();
        context.getTypeConverterRegistry().addTypeConverters(myTypeConverters);
    }
}
