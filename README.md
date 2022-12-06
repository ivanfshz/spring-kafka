# Spring Cloud Kafka Project - Azure Spring App


## Getting Started

This spring-boot application takes a dynamic xml, convert it to json format and save it into mongo db as a dynamic object.

It  also contains a client to query the inserted entity. This Rest API contains the next

- rest api endpoint to send any xml messages to a given kafka topic.
- rest api endpoint to query a given key message and see what it looks like as json format

### Process flow:
 
- A message is sent to the endpoint rest api to be saved in a json format in mongodb
- A producer will publish the message into a given kafka topic
- A consumer will read the message, deserialize it and build a DBJsonObject to save it

## Requirements

This project includes the next stack tech:

* spring boot 3.0
* Maven
* MongoDB
* spring kafka
* HATEOAS
* Actuator
* Azure Spring App

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/#build-image)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#messaging.kafka)
* [Spring Data Reactive MongoDB](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#data.nosql.mongodb)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

## How to run 

1. Replace credentials in application.properties file (clonfluent & mongoDB uri)

2. Start up the application:

```
./mvnw  spring-boot:run
```

3. Post any xml message to the kafka topic through the rest api:
4. 
```
curl --request POST \
--url http://localhost:8080/dynamic-xml-json/publish \
--header 'Content-Type: application/xml' \
--data '<?xml version="1.0" encoding="UTF-8"?>
<emails>  
<email>  
  <to>Vimal</to>  
  <from>Sonoo</from>  
  <heading>Hello</heading>  
  <body>Hello brother, how are you!</body>  
</email>  
<email>  
  <to>Peter</to>  
  <from>Jack</from>  
  <heading>Birth day wish</heading>  
  <body>Happy birth day Tom!</body>  
</email>  
<email>  
  <to>James</to>  
  <from>Jaclin</from>  
  <heading>Morning walk</heading>  
  <body>Please start morning walk to stay fit!</body>  
</email>  
<email>  
  <to>Kartik</to>  
  <from>Kumar</from>  
  <heading>Health Tips</heading>  
  <body>Smoking is injurious to health!</body>  
</email>  
</emails>'
```

4. You will get a response like this:
```
{
  "key": "794",
  "message": "all good :)!",
  "content": {
    "timestamp": 1670290164499,
    "topic": "db_writer",
    "partition": 4,
    "offset": 12
  },
  "_links": {
    "self": {
      "href": "/dynamic-xml-json/794"
    }
  }
}
```

5. You can query the item by its key to see what it looks like as json format in mongodb:

```
   curl --request GET \
   --url http://localhost:8080/dynamic-xml-json/794
```
 
6. You will ger a response like this :

``` 
{
   "key": "794",
   "message": "all good :)!",
   "content": {
   "emails": {
   "email": [
   {
   "heading": "Hello",
   "from": "Sonoo",
   "to": "Vimal",
   "body": "Hello brother, how are you!"
   },
   {
   "heading": "Birth day wish",
   "from": "Jack",
   "to": "Peter",
   "body": "Happy birth day Tom!"
   },
   {
   "heading": "Morning walk",
   "from": "Jaclin",
   "to": "James",
   "body": "Please start morning walk to stay fit!"
   },
   {
   "heading": "Health Tips",
   "from": "Kumar",
   "to": "Kartik",
   "body": "Smoking is injurious to health!"
   }
   ]
   },
   "_id": {
   "$oid": "638e9af5de908171cc377822"
   },
   "_key": "794"
   },
   "_links": {
   "self": {
   "href": "http://localhost:8080/dynamic-xml-json/794"
   }
   }
   }
```
