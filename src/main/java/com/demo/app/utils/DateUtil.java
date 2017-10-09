package com.demo.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public enum DateUtil {

    INSTANCE;

    public DateRange getDateRange(String providedDate, String type)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try
        {
            date  = dateFormat.parse(providedDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        switch(type) {
            case "quarter":
                return getDateRangeForQuarter(date);
            case "week":
                return getDateRangeForWeek(date);
            case "month":
                return getDateRangeForMonth(date);
            default:
                return null;
        }
    }

    public DateRange getDateRangeForQuarter(Date providedDate)
    {
        String startDate = null;
        String endDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar providedCal = Calendar.getInstance();
        providedCal.setTime(providedDate);

        int month = providedCal.get(Calendar.MONTH);
        if(month >= Calendar.JANUARY && month <= Calendar.MARCH)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(providedCal.get(Calendar.YEAR), 0, 1);
            startDate = dateFormat.format(cal.getTime());
            cal.set(providedCal.get(Calendar.YEAR),2,31);
            endDate = dateFormat.format(cal.getTime());
            return  new DateRange(startDate, endDate);
        }
        else if(month >= Calendar.APRIL && month <= Calendar.JUNE)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(providedCal.get(Calendar.YEAR), 3, 1);
            startDate = dateFormat.format(cal.getTime());
            cal.set(providedCal.get(Calendar.YEAR),5,31);
            endDate = dateFormat.format(cal.getTime());
            return new DateRange(startDate, endDate);
        }
        else if(month >= Calendar.JULY && month <= Calendar.SEPTEMBER)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(providedCal.get(Calendar.YEAR), 6, 1);
            startDate = dateFormat.format(cal.getTime());
            cal.set(providedCal.get(Calendar.YEAR),8,31);
            endDate = dateFormat.format(cal.getTime());
            return new DateRange(startDate, endDate);
        }
        else
        {

            Calendar cal = Calendar.getInstance();
            cal.set(providedCal.get(Calendar.YEAR), 9, 1);
            startDate = dateFormat.format(cal.getTime());
            cal.set(providedCal.get(Calendar.YEAR),11,31);
            endDate = dateFormat.format(cal.getTime());
            return new DateRange(startDate, endDate);
        }
    }

    public DateRange getDateRangeForMonth(Date providedDate)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar providedCal = Calendar.getInstance();
        providedCal.setTime(providedDate);
        Calendar cal = Calendar.getInstance();
        cal.set(providedCal.get(Calendar.YEAR), providedCal.get(Calendar.MONTH), 1);
        int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String startDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
        String endDate = dateFormat.format(cal.getTime());
        return new DateRange(startDate,endDate);
    }

    public DateRange getDateRangeForWeek(Date providedDate)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar providedCal = Calendar.getInstance();
        providedCal.setTime(providedDate);
        providedCal.set(providedCal.get(Calendar.YEAR), providedCal.get(Calendar.MONTH), 1);
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(providedCal.getTimeInMillis());

        int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK);
        startCal.set(Calendar.DAY_OF_MONTH,
                (startCal.get(Calendar.DAY_OF_MONTH) - dayOfWeek) + 1);

        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(providedCal.getTimeInMillis());

        dayOfWeek = endCal.get(Calendar.DAY_OF_WEEK);
        endCal.set(Calendar.DAY_OF_MONTH, endCal.get(Calendar.DAY_OF_MONTH)
            + (7 - dayOfWeek));

        return new DateRange(dateFormat.format(startCal.getTime()),dateFormat.format(endCal.getTime()));

    }

}
