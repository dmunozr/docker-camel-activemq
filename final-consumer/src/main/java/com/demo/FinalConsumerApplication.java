package com.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.restlet.RestletComponent;
import org.apache.camel.spring.boot.FatJarRouter;
import org.restlet.Component;
import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinalConsumerApplication extends FatJarRouter {

    @Override
    public void configure() {
        restConfiguration().component("restlet");
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        final SpringServerServlet serverServlet = new SpringServerServlet();
        final ServletRegistrationBean regBean = new ServletRegistrationBean(serverServlet, "/rest/*");
        final Map<String, String> params = new HashMap<>();
        params.put("org.restlet.component", "restletComponent");
        regBean.setInitParameters(params);

        return regBean;
    }

    @Bean
    public Component restletComponent() {
        return new Component();
    }

    @Bean
    public RestletComponent restletComponentService() {
        return new RestletComponent(restletComponent());
    }

}
