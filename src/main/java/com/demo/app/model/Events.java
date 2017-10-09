package com.demo.app.model;

import java.util.List;

public class Events {

    private int totalEvents;

    private List<Event> events;

    public Events(List<Event> events, int totalEvents) {
        this.events = events;
        this.totalEvents = totalEvents;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }
}
