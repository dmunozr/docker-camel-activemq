package com.test.controllers;

import com.test.dto.CarBudgetsRequest;
import com.test.service.CarBudgetsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/requests")
public class CarBudgetsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBudgetsController.class);

    @Autowired
    private CarBudgetsService carBudgetsService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final CarBudgetsRequest carBudgetsRequest) {
        LOGGER.debug("Creating {} car budget requests and sending them to the queue!", carBudgetsRequest.getRequests().size());
    }
}
