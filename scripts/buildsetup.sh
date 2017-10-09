#!/bin/bash
curl -X DELETE 'http://localhost:9200/_all'
curl -XPUT http://localhost:9200/events?pretty=true -d '
{
    "settings" : {
        "index" : {
            "number_of_shards" : 5,
            "number_of_replicas" : 0
        }
    }
}
'
curl -XPUT http://localhost:9200/events/_mapping/event?pretty=true -d '
{
    "event" : {
        "properties" : {
            "event_date" : { "type" : "date", "store" : true },
            "event_type" : { "type" : "string", "store" : true },
            "event_summary" : { "type" : "string" },
            "event_metric" : { "type" : "integer" },
            "event_details" : { "type" : "string" }
        }
    }
}
'
awk '/^"/ {if (f) print f; f=$0; next} {f=f FS $0} END {print f}' $1 > /tmp/formatted.json
cat /tmp/formatted.json | jq -c '.[] | {"index": {"_index": "events", "_type": "event"}}, .'> /tmp/elastic.json
cd /tmp
split -l 1000 /tmp/elastic.json SplitJson
for i in `ls SplitJson*`; do curl -s -XPOST http://localhost:9200/events/event/_bulk --data-binary @$i; done
for i in `ls SplitJson*`; do rm -rf $i; done
cd -
