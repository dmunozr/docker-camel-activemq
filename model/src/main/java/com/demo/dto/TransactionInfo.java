package com.demo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionInfo extends BaseObject implements Serializable {

    @JsonProperty
    String sponsorId;

    @JsonProperty
    Float minPrice;

    @JsonProperty
    Float maxPrice;

    @JsonProperty
    Availability availability;

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(final String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(final Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(final Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(final Availability availability) {
        this.availability = availability;
    }
}
