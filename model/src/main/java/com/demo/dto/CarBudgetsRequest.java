package com.demo.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarBudgetsRequest extends BaseObject implements Serializable {

    @JsonProperty
    private List<CarBudgetRequest> requests;

    public List<CarBudgetRequest> getRequests() {
        return requests;
    }
}
