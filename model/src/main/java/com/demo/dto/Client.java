/*
 * All rights reserved. Copyright (c) Ixxus Ltd 2017
 */
package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {

    @JsonProperty
    String firstName;

    @JsonProperty
    String lastName;

    @JsonProperty
    String telephone;

    @JsonProperty
    String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
