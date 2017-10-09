package com.demo.app.service;

import com.demo.app.model.Event;
import com.demo.app.model.Events;
import com.demo.app.model.QueryMeta;
import com.demo.app.utils.DateRange;
import com.demo.app.utils.DateUtil;
import com.demo.elastic.model.Hit;
import com.demo.elastic.model.ResponseHits;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Scope(value = "singleton")
@Component
public class ElasticService {

    private RestClient restClient;

    public ElasticService(){
        Header[] headers = { new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader("Role", "Read") };
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
        restClient = RestClient.builder(new HttpHost("localhost", 9200)).setDefaultHeaders(headers)
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder arg0) {

                        return arg0.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
                .build();
    }

    public Event fetchEvent(String id) throws IOException
    {
        String queryString = "{\n" +
                "  \"query\": { \n" +
                "      \"bool\": {\n" +
                "          \"filter\":{\n" +
                "              \"term\":{\"_id\": \"" + id + "\"\n" +
                "              }\n" +
                "          }\n" +
                "      }\n" +
                "   },\n" +
                "  \"_source\" : [\"event_date\",\"event_type\",\"event_summary\",\"event_size\",\"event_details\"]\n" +
                "}";
        Response response = restClient.performRequest("POST", "/events/_search", new Hashtable<>(), new StringEntity(queryString,ContentType.APPLICATION_JSON));
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String str = EntityUtils.toString(response.getEntity());
        ResponseHits responseHits = jacksonObjectMapper.readValue(str, ResponseHits.class);
        List<Hit> hitList = responseHits.getHits().getHits();
        Event event = null;
        for(Hit lHit : hitList)
        {
            event = lHit.getSource();
            event.setId(lHit.getId());
            break;
        }
        return event;
    }

    public QueryMeta getCount(String startDate, String period) throws IOException
    {
        DateRange dateRange = DateUtil.INSTANCE.getDateRange(startDate, period.toLowerCase());
        int fromRecordNum = 0;

        String queryString = "{\n" +
                "  \"query\": {\n" +
                "    \"range\": {\n" +
                "      \"event_date\": {\n" +
                "        \"gte\": \"" +  dateRange.getStartDate() + "\",\n" +
                "        \"lte\": \""+ dateRange.getEndDate() +"\",\n" +
                "        \"format\": \"yyyy-MM-dd\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        Response response = restClient.performRequest("POST", "/events/_count", new Hashtable<>(), new StringEntity(queryString,ContentType.APPLICATION_JSON));
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String str = EntityUtils.toString(response.getEntity());
        QueryMeta queryMeta = jacksonObjectMapper.readValue(str, QueryMeta.class);
        return queryMeta;
    }

    public Events fetchAllEvents(int pageNumber, int pageSize, String startDate, String period) throws IOException
    {

        DateRange dateRange = DateUtil.INSTANCE.getDateRange(startDate, period.toLowerCase());

        int fromRecordNum = 0;
        if(pageNumber > 1)
        {
            fromRecordNum = (pageNumber-1) * pageSize;
        }
        String queryString = "{\n" +
                "\"from\": " + fromRecordNum + ",\n" +
                "  \"size\": " + pageSize + ",\n" +
                "  \"sort\": [\n" +
                "    {\n" +
                "      \"event_date\": {\n" +
                "        \"order\": \"asc\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]," +
                "  \"query\": {\n" +
                "    \"range\": {\n" +
                "      \"event_date\": {\n" +
                "        \"gte\": \"" +  dateRange.getStartDate() + "\",\n" +
                "        \"lte\": \""+ dateRange.getEndDate() +"\",\n" +
                "        \"format\": \"yyyy-MM-dd\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"_source\" : [\"event_date\",\"event_type\",\"event_summary\",\"event_size\"]\n" +
                "}";

        Response response = restClient.performRequest("GET", "/events/_search", new Hashtable<>(), new StringEntity(queryString,ContentType.APPLICATION_JSON));
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String str = EntityUtils.toString(response.getEntity());
        ResponseHits responseHits = jacksonObjectMapper.readValue(str, ResponseHits.class);
        List<Hit> hitList = responseHits.getHits().getHits();
        List<Event> eventList = new LinkedList<>();
        for(Hit lHit : hitList)
        {
            Event event = lHit.getSource();
            event.setId(lHit.getId());
            eventList.add(event);

        }
        Events events = new Events(eventList, responseHits.getHits().getTotal());
        return events;
    }

}
