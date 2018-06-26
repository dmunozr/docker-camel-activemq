package com.demo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdditionalInfo extends BaseObject implements Serializable {

    @JsonProperty
    private float minPrice;

    @JsonProperty
    private float maxPrice;

    @JsonProperty
    private Availability availability;

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(final float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(final float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(final Availability availability) {
        this.availability = availability;
    }
}
