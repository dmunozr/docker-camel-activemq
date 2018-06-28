package com.demo.camel.processor;

import static com.demo.camel.component.ImgFinderConstants.RESULT_HEADER_NAME;
import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InjectImageUrlProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(InjectImageUrlProcessor.class);

    @Override
    public void process(final Exchange exchange) throws ParseException {
        final String imageUrl = (String) exchange.getIn().getHeader(RESULT_HEADER_NAME);
        if (StringUtils.isNotBlank(imageUrl)) {
            final String json = (String) exchange.getIn().getBody();

            final JSONObject jsonObject = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(json);
            final JSONObject car = (JSONObject) jsonObject.get("car");
            car.put("imageUrl", imageUrl);
            exchange.getOut().setBody(jsonObject.toJSONString());
        } else {
            LOG.warn("Skipping injecting process due to the imageUrl retrieved is null!");
        }
    }
}
