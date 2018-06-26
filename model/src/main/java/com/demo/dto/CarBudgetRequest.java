package com.demo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarBudgetRequest extends BaseObject implements Serializable {

    @JsonProperty
    private TransactionInfo transactionInfo;

    @JsonProperty
    private AdditionalInfo additionalInfo;

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

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(final AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
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
