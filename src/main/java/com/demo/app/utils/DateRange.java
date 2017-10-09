package com.demo.app.utils;

public class DateRange {

    private String startDate;
    private String endDate;

    public DateRange(String startDate, String endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
