package com.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarBudgetsRequest {

    @JsonProperty
    private List<CarBudgetRequest> requests;

    public List<CarBudgetRequest> getRequests() {
        return requests;
    }
}
