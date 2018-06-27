package com.demo.filter;

import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CarBrandFilter {

    @Value("${car.brand.header.name}")
    private String carBrandHeaderName;

    @Value("#{'${car.brand}'.split(',')}")
    private Set<String> carBrands;

    public boolean filter(final Exchange exchange) {
        final String carBrand = (String) exchange.getIn().getHeader(carBrandHeaderName);
        boolean isAllowedBrand = false;
        if (StringUtils.isNotBlank(carBrand)) {
            isAllowedBrand = carBrands.contains(carBrand.toLowerCase());
        }

        return isAllowedBrand;
    }
}
