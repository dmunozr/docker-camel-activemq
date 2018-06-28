package com.demo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car extends BaseObject implements Serializable {

    @JsonProperty
    private String brand;

    @JsonProperty
    private String model;

    @JsonProperty
    private String version;

    @JsonProperty
    private Short year;

    @JsonProperty
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
