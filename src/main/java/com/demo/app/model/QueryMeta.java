package com.demo.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryMeta {


    private String numRecords;

    @JsonProperty(value = "totalEvents")
    public String getNumRecords() {
        return numRecords;
    }
    @JsonProperty(value = "count")
    public void setNumRecords(String numRecords) {
        this.numRecords = numRecords;
    }
}
