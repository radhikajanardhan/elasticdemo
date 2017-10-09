# elasticdemo

What this application uses?
a) ElasticSearch for storing Json data pertaining to events.
b) SpringBoot for defining the REST services.
c) JSP & JQuery for UI


ElasticSearch Version : 5.6.2
Download Link : https://www.elastic.co/guide/en/elasticsearch/reference/current/zip-targz.html

You could also install the ElasticSearch head plugin to view records / execute commands in Elastic.
Download Link : https://github.com/mobz/elasticsearch-head

Refer to https://github.com/radhikajanardhan/elasticdemo/blob/master/scripts/buildsetup.sh

Pre-requisite:
--------------
The script performs the following
a) Clean up the ElasticSearch DB
b) Create Elastic Index
c) Create Type for event that would be stored in the DB
d) The JSON that could be provided may have new lines within string content. The awk scripts fix them
e) For inserting in bulk, we need to define the index / type details. We use JQ (https://stedolan.github.io/jq/) to fill in those details into the Bulk JSON
f) Bulk would not work with large files. So we need to split them and load them one by one

Once the steps in the file is executed, the database is ready with the data for us to run the demo.

To Build:
---------
gradle clean build

This generates a WAR file (./build/libs/elasticdemo.war) that can be deployed into the web container.

Once deployed, the URL to run the demo is http://<server ip>:<port>/ElasticDemo/
  
ElasticSearch tools

http://localhost:9100/ - To check if the Elastic DB is running

http://localhost:9200/ - To access the Elastic head plugin
