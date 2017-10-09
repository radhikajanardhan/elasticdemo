package com.demo.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Event {

    @JsonProperty(value = "_id")
    private String _id;
    @JsonProperty(value = "event_date")
    private String date;
    @JsonProperty(value = "event_type")
    private String type;
    @JsonProperty(value = "event_summary")
    private String summary;
    @JsonProperty(value = "event_size")
    private int size;
    @JsonProperty(value = "event_details")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String details;

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getSummary() {
        return summary;
    }

    public int getSize() {
        return size;
    }

    public String getId(){
        return _id;
    }

    public String getDetails() {
        return details;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setId(String id)
    {
         this._id = id;
    }


    public void setDetails(String details) {
        this.details = details;
    }
}
