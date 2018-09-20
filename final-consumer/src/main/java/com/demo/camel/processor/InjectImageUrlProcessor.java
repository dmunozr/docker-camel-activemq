package com.demo.camel.processor;

import static com.demo.camel.component.ImgFinderConstants.RESULT_HEADER_NAME;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InjectImageUrlProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(InjectImageUrlProcessor.class);

    @Override
    public void process(final Exchange exchange) {
        final String imageUrl = (String) exchange.getIn().getHeader(RESULT_HEADER_NAME);
        if (StringUtils.isNotBlank(imageUrl)) {
            final String json = (String) exchange.getIn().getBody();
            final JSONTokener tokener = new JSONTokener(json);
            final JSONObject jsonObject = new JSONObject(tokener);
            final JSONObject car = (JSONObject) jsonObject.get("car");
            car.put("imageUrl", imageUrl);
            exchange.getOut().setBody(jsonObject);
        } else {
            LOG.warn("Skipping injecting process due to the imageUrl retrieved is null!");
        }
    }
}
