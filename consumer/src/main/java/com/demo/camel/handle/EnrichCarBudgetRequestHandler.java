package com.demo.camel.handle;

import com.demo.dto.AdditionalInfo;
import com.demo.dto.Availability;
import com.demo.dto.CarBudgetRequest;
import com.demo.dto.TransactionInfo;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnrichCarBudgetRequestHandler {

    private static final float MIN_PRICE = 20000;
    private static final float MIN_PRICE_LIMIT = 50000;
    private static final float MAX_DIFFERENCE_BETWEEN_MIN_MAX = 10000;

    @Value("${sponsorId.header.name}")
    private String sponsorIdHeaderName;

    @Value("${car.brand.header.name}")
    private String carBrandHeaderName;

    @Handler
    public void handle(final Exchange exchange) {
        final Message message = exchange.getIn();
        final String sponsorId = message.getHeader(sponsorIdHeaderName, String.class);
        final CarBudgetRequest carBudgetRequest = (CarBudgetRequest) message.getBody();
        carBudgetRequest.setTransactionInfo(generateTransactionInfo(sponsorId));
        carBudgetRequest.setAdditionalInfo(generateAdditionalInfo());
        exchange.getOut().setBody(carBudgetRequest);
        exchange.getOut().removeHeader(sponsorIdHeaderName);
        exchange.getOut().setHeader(carBrandHeaderName, carBudgetRequest.getCar().getBrand());
    }

    private TransactionInfo generateTransactionInfo(final String sponsorId) {
        final TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setSponsorId(sponsorId);

        return transactionInfo;
    }

    private AdditionalInfo generateAdditionalInfo() {
        final AdditionalInfo additionalInfo = new AdditionalInfo();
        final float minPrice = RandomUtils.nextFloat(MIN_PRICE, MIN_PRICE_LIMIT);
        additionalInfo.setMinPrice(minPrice);
        additionalInfo.setMaxPrice(minPrice + RandomUtils.nextFloat(0, MAX_DIFFERENCE_BETWEEN_MIN_MAX));
        additionalInfo.setAvailability(generateAvailability());

        return additionalInfo;
    }

    private Availability generateAvailability() {
        return Availability.values()[RandomUtils.nextInt(0, Availability.values().length)];
    }

}
