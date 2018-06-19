/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car implements Serializable {

    @JsonProperty
    String brand;

    @JsonProperty
    String model;

    @JsonProperty
    String version;

    @JsonProperty
    Short year;

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(final Short year) {
        this.year = year;
    }
}
