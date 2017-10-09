package com.demo.app.controller;

import com.demo.app.model.Event;
import com.demo.app.model.Events;
import com.demo.app.model.QueryMeta;
import com.demo.app.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value="/rest/event")

public class EventController {

    @Autowired
    private ElasticService elasticService;

    @GetMapping(value = "/count", produces = "application/json")
    public QueryMeta getCount(@RequestParam(value="startDate")String startDate,
                              @RequestParam(value="period")String period)
    {
        try
        {
            return elasticService.getCount(startDate, period);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        QueryMeta queryMeta = new QueryMeta();
        queryMeta.setNumRecords(String.valueOf(0));
        return queryMeta;
    }

    @GetMapping(value = "/fetchAll", produces = "application/json")
    public Events getAllEvents(@RequestParam(value="pageNumber")int pageNumber,
                               @RequestParam(value="startDate")String startDate,
                               @RequestParam(value="period")String period,
                               @RequestParam(value="pageSize", required = false, defaultValue = "25") int pageSize)
    {
        try
        {
            return elasticService.fetchAllEvents(pageNumber, pageSize, startDate, period);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new Events(Collections.EMPTY_LIST, 0);
    }

    @GetMapping(value = "/fetchEvent", produces = "application/json")
    public Event getEvent(@RequestParam(value="id")String id)
    {
        try
        {
            return elasticService.fetchEvent(id);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
