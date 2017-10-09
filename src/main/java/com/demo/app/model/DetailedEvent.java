package com.demo.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailedEvent extends Event {

    @JsonProperty(value = "event_details")
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
