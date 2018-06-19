/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarBudgetRequest {

    @JsonProperty
    private TransactionInfo transactionInfo;

    @JsonProperty
    private Car car;

    @JsonProperty
    private Client client;

    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(final TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(final Car car) {
        this.car = car;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(final Client client) {
        this.client = client;
    }
}
